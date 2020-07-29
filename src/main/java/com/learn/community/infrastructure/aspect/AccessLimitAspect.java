package com.learn.community.infrastructure.aspect;

import com.learn.community.infrastructure.annotation.AccessLimit;
import com.learn.community.infrastructure.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Scope
@Aspect
public class AccessLimitAspect {

    @Resource
    private HttpServletResponse response;

    @Pointcut("@annotation(com.learn.community.infrastructure.annotation.AccessLimit)")
    public void limitService() {}

    /**
     * 这里可以写具体的路径 module包下所有的方法都会调用这个方法  @Around("execution(* com.ehr.module.*.*(..))")
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("limitService()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取拦截的方法相关信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //获取注解信息
        AccessLimit accessLimit = currentMethod.getAnnotation(AccessLimit.class);

        // 限流策越，根据package和方法名称组成Key
        String packageName = (methodSignature.getMethod().getDeclaringClass()).getName();
        String functionKey = packageName +"_API>"+ methodSignature.getName();

        //最大次数
        int maxLimit = accessLimit.limit();
        //多长时间的最大次数
        int time = accessLimit.time();

        Object limit = RedisUtil.StringOps.get(functionKey);
        if (limit == null) {
            RedisUtil.StringOps.setEx(functionKey, "1", time, TimeUnit.MILLISECONDS);
            return joinPoint.proceed();
        } else if (Integer.parseInt(limit.toString()) < maxLimit) {
            RedisUtil.StringOps.setEx(functionKey, (Integer.parseInt(limit.toString()) + 1) + "", time, TimeUnit.MILLISECONDS);
            return joinPoint.proceed();
        } else {
            log.info("当前 {} 请求超出设定的访问次数，请稍后再试！",functionKey);
            output(response, "当前请求超出设定的访问次数，请稍后再试！");
        }
        return null;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}