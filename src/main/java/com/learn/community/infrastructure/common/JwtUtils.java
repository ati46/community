package com.learn.community.infrastructure.common;

import com.learn.community.domain.bean.mysql.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtils {
    /**
     * 签发JWT
     * @param ttlMillis
     * @return  String
     *
     */
    public static String createJWT(Users users, long ttlMillis){
        return create(users, ttlMillis);
    }
    public static String createJWT(Users users){
        return create(users, SystemConstant.JWT_TIMEOUT);
    }
    private static String create(Users users, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(users.getId()))
                .setSubject(users.getName())   // 主题
                .setIssuer("ati")     // 签发者
                .setIssuedAt(now)      // 签发时间
                // 自定义属性 放入用户拥有权限
                .claim("role", users.getRoleId())
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = SystemConstant.JWT_SECERT.getBytes();
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
//        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
//        byte[] baa = DatatypeConverter.
//                parseBase64Binary(SystemConstant.JWT_SECERT);
//        return  new SecretKeySpec(baa,hs256.getJcaName());
    }

    /**
     *
     * 解析JWT字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwt)
            .getBody();
    }

    public static String getId(String jwt) throws Exception {
        Claims claims = parseJWT(jwt);
        return claims.getId();
    }
}