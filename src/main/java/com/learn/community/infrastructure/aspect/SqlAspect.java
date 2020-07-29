package com.learn.community.infrastructure.aspect;

import com.learn.community.infrastructure.utils.SqlUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SqlAspect {
 
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
 
 
    @Pointcut("@annotation(com.learn.community.infrastructure.annotation.SqlCopy)")
    private void pc(){
 
    }
 
    //前置通知
    //指定该方法是前置通知，并指定切入点
    @Before("SqlAspect.pc()")
    public void before(){
//        System.out.println("这是前置通知！！！！！");
    }
 
    //后置通知
    @AfterReturning("SqlAspect.pc()")
    public void afterReturning() {
//        System.out.println("这是后置通知！(如果出现异常，将不会调用)！！！！");
    }
 
    //环绕通知
    @Around("SqlAspect.pc()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        //1.从redis中获取主数据库，若获取不到直接退出，否则判断当前数据源是会否为主，若不为主，则切换到主数据源
        //2.调用目标方法
        Object proceed = pjp.proceed();
        //3.获取SQL
        String sql = SqlUtils.getMybatisSql(pjp,sqlSessionFactory);
        System.out.println(sql);
        //4.插入日志
        //5.通知同步程序
        return proceed;
    }

    //异常通知
    @AfterThrowing("SqlAspect.pc()")
    public void afterException(){
//        System.out.println("出事了，抛异常了！！！！");
    }
 
    //后置通知
    @After("SqlAspect.pc()")
    public void after(){
//        System.out.println("这是后置通知！(无论是否出现异常都会调用)！！！！");
    }
}