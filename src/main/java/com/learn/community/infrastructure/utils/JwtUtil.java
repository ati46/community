package com.learn.community.infrastructure.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.learn.community.infrastructure.common.RedisKey;
import com.learn.community.infrastructure.common.RedisUtil;
import com.learn.community.infrastructure.common.SystemConstant;
import com.learn.community.infrastructure.response.BaseException;
import com.learn.community.infrastructure.response.ResponseCode;
import com.learn.community.infrastructure.security.SecurityUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * jwt工具类
 */
@Configuration
@Slf4j
public class JwtUtil {

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe, Boolean isRefresh) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return createJWT(isRefresh, rememberMe, user.getId(), user.getLoginName(), user.getRoleId(), user.getAuthorities());
    }

    /**
     * 创建JWT
     *
     * @param id          用户id
     * @param loginName     用户名
     * @param roleId       用户角色
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJWT(Boolean isRefresh,
                            Boolean rememberMe,
                            int id,
                            String loginName,
                            int roleId,
                            Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        String aa = SystemConstant.JWT_SECERT;
        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(id))
                .setSubject(loginName)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SystemConstant.JWT_SECERT)
                .claim("roles", roleId)
                // .claim("perms", menus)
                .claim("authorities", authorities);

        // 设置过期时间
        Long ttl = rememberMe ? SystemConstant.JWT_REMEMBER : SystemConstant.JWT_TIMEOUT;
        String redisKey;
        if (isRefresh){
            ttl *= 3;
            redisKey = String.format(RedisKey.User.REFRESH_TOKEN, loginName);
        }else{
            redisKey = String.format(RedisKey.User.TOKEN, loginName);
        }
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        RedisUtil.StringOps.setEx(redisKey, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt, Boolean isRefresh) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SystemConstant.JWT_SECERT)
                    .parseClaimsJws(jwt)
                    .getBody();

            String loginName = claims.getSubject();
            String redisKey = String.format((isRefresh ? RedisKey.User.REFRESH_TOKEN : RedisKey.User.TOKEN), loginName);

            // 校验redis中的JWT是否存在
            Long expire = RedisUtil.KeyOps.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new BaseException(ResponseCode.TOKEN_EXPIRED);
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = (String) RedisUtil.StringOps.get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new BaseException(ResponseCode.TOKEN_OUT_OF_CTRL);
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new BaseException(ResponseCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new BaseException(ResponseCode.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new BaseException(ResponseCode.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new BaseException(ResponseCode.TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String loginName = getLoginNameFromJWT(jwt, false);
        // 从redis中清除JWT
        RedisUtil.KeyOps.delete(String.format(RedisKey.User.REFRESH_TOKEN, loginName));
        RedisUtil.KeyOps.delete(String.format(RedisKey.User.TOKEN, loginName));
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getLoginNameFromJWT(String jwt, Boolean isRefresh) {
        Claims claims = parseJWT(jwt, isRefresh);
        return claims.getSubject();
    }

    public Map<String, String> refreshJWT(String token) {
        Claims claims = parseJWT(token, true);
        // 获取签发时间
        Date lastTime = claims.getExpiration();
        // 1. 判断refreshToken是否过期
        if (!new Date().before(lastTime)){
            throw new BaseException(ResponseCode.TOKEN_EXPIRED);
        }
        // 2. 在redis中删除之前的token和refreshToken
        String username = claims.getSubject();
        // redisTemplate.delete(Constant.REDIS_JWT_REFRESH_TOKEN_KEY_PREFIX + username);
        // redisTemplate.delete(Constant.REDIS_JWT_TOKEN_KEY_PREFIX + username);
        // 3. 创建新的token和refreshToken并存入redis
        String jwtToken = createJWT(false, false, Integer.parseInt(claims.getId()), username,
                Integer.parseInt(claims.get("roles").toString()), (Collection<? extends GrantedAuthority>) claims.get("authorities"));
        String refreshJwtToken = createJWT(true, false, Integer.parseInt(claims.getId()), username,
                Integer.parseInt(claims.get("roles").toString()), (Collection<? extends GrantedAuthority>) claims.get("authorities"));
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("refreshToken", refreshJwtToken);
        return map;
    }

    /**
     *
     * 功能：生成 jwt token<br/>
     * @param name	实例名
     * @param param	需要保存的参数
     * @param secret	秘钥
     * @param expirationtime	过期时间(5分钟 5*60*1000)
     * @return
     *
     */
    public static String sign(String name, Map<String,Object> param, String secret, Long expirationtime){
        String JWT = Jwts.builder()
                .setClaims(param)
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + expirationtime))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
        return JWT;
    }
    /**
     *
     * 功能：解密 jwt<br/>
     * @param JWT	token字符串
     * @param secret	秘钥
     * @return
     * @exception
     *
     */
    public static Claims verify(String JWT, String secret){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(JWT)
                .getBody();
        return claims;
    }

    public static Object getValueFromToken(String jwt,String key, String secret){
        return verify(jwt, secret).get(key);
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVf6LaF57qn566h55CG5ZGYIl0sImV4cCI6MTU2MjgzMjU5Nn0.eH57RyBH-Kwct6GmLyHDN9TjRTF02wDNma-NKZx711w";
        Claims claims = verify(token, "janche");
        Object value = getValueFromToken(token, "roles", "janche");
        System.out.println(value);
        System.out.println(claims);
    }

}
