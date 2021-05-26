package com.cqjtu.dpta.web.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.dao.entity.DistrUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/4/25
 */
@Aspect
@Component
public class  UserCheckAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Before("@annotation(com.cqjtu.dpta.web.security.UserCheck)")
    public void process(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();

        UserCheck userCheck = methodSignature.getMethod().getAnnotation(UserCheck.class);

        UserChecker userChecker = applicationContext.getBean(userCheck.value());

        Object[] args = jp.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

        System.out.println(parameterAnnotations.length);

        Map<String, Object> argsMap = new HashMap<>();
        MinUser minUser = null;
        for (int i = 0; i < parameterAnnotations.length; i++) {
            if (args[i] instanceof MinUser) {
                minUser = (MinUser) args[i];
            }
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof CheckParam) {
                    CheckParam checkParam = (CheckParam) annotation;
                    if (checkParam.name().equals("")) {
                        argsMap.put(parameterNames[i], args[i]);
                    }
                }
            }
        }

        userChecker.check(minUser, argsMap);
    }

    private static QueryWrapper<DistrUser> getQueryWrapper(String username) {
        return new QueryWrapper<DistrUser>().eq("username", username);
    }
}
