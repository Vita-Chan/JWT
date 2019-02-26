package com.vita.jwt.jwt2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要登录才能进行操作的注解
 *
 * @Target({ElementType.METHOD, ElementType.TYPE}): 注解的作用目标是 接口、类、枚举、注解, 方法
 * @Retention(RetentionPolicy.RUNTIME): 该注解将保留再 jwt运行中
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {

  boolean required() default true;
}