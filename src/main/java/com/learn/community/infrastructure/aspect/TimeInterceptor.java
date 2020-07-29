package com.learn.community.infrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 检测方法执行耗时的aop切面类
 * 当配置中envconfig.statistic.enabled为true时启用
 */
@Slf4j
@Aspect
@Component
public class TimeInterceptor {

    // 一分钟，即60000ms
    private static final long ONE_MINUTE = 0;

    // service层的统计耗时切面，类型必须为final String类型的,注解里要使用的变量只能是静态常量类型的
    private static final String SERVICE_POINT = "execution (* com.learn.community.application.*.*.*(..))";
    private static final String REPO_POINT = "execution (* com.learn.community.infrastructure.*.*.*.*(..))";
    private static final String DOMIAN_POINT = "execution (* com.learn.community.domain.*.*.*.*(..))";

    @Around(SERVICE_POINT)
    public Object serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return calculateTime(joinPoint);
    }

    @Around(REPO_POINT)
    public Object repoAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return calculateTime(joinPoint);
    }

    @Around(DOMIAN_POINT)
    public Object domainAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return calculateTime(joinPoint);
    }

    private Object calculateTime(ProceedingJoinPoint joinPoint) throws Throwable{
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
            log.error("统计某方法执行耗时环绕通知出错 "+ e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }

        // 获取执行的方法名
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        // 打印耗时的信息
        this.printExecTime(methodName, startTime, endTime);

        return obj;
    }
    /**
     * 打印方法执行耗时的信息，如果超过了一定的时间，才打印
     * @param methodName
     * @param startTime
     * @param endTime
     */
    private void printExecTime(String methodName, long startTime, long endTime) {
        long diffTime = endTime - startTime;
        if (diffTime > ONE_MINUTE) {
            log.warn("-----" + methodName + " 方法执行耗时：" + diffTime + " ms");
        }
    }

}

