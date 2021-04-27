package com.cqjtu.dpta.web.security;

import com.cqjtu.dpta.web.support.BigUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reactor.util.annotation.Nullable;

/**
 * author: mumu
 * date: 2021/4/25
 */
public class UniqueUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MinUser.class.equals(parameter.getParameterType());
    }

    @Override
    public MinUser resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        return SecurityUtil.minUserOpt().orElseThrow(IllegalStateException::new);
    }


}
