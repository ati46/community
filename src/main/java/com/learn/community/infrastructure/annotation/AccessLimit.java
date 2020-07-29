package com.learn.community.infrastructure.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    //标识 指定time时间段内的访问次数限制
    int limit() default 5;

    //标识 时间段 分钟
    int time() default 1;
}
