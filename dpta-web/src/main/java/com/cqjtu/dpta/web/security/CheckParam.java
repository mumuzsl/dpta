package com.cqjtu.dpta.web.security;

import cn.hutool.core.annotation.Alias;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: mumu
 * date: 2021/4/26
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
