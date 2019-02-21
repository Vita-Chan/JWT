package com.vita.jwt.jwt1.annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用来跳过验证的PassToken
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
  boolean required() default true;
}