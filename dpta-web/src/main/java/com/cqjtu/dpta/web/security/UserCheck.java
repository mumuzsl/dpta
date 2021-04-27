package com.cqjtu.dpta.web.security;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户检查注解，添加在方法上，
 * author: mumu
 * date: 2021/4/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserCheck {

    Class<? extends UserChecker> value();

}
