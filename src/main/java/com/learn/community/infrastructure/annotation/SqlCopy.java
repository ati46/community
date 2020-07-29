package com.learn.community.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据拷贝注解，当数据插入或更新到mysql的时候 同步插入一份到mongodb
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlCopy{
   
    String value() default "";
}