package com.cqjtu.dpta.web.security;

import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.web.support.BigUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * author: mumu
 * date: 2021/4/30
 */
public class InfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Info.class.equals(parameter.getParameterType());
    }

    @Override
    public Info resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Assert.notNull(request, "request not be null");
        Long userId = SecurityUtil.bigUserOpt().map(BigUser::getId).orElseThrow(() -> new RuntimeException("未能获取用户信息"));
        return Info.of(userId);
    }

}
