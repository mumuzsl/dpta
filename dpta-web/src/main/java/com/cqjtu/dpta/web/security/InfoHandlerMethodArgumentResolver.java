package com.cqjtu.dpta.web.security;

import com.cqjtu.dpta.web.support.Info;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

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
        Info info = null;
        try {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            Assert.notNull(request, "request not be null");
            info = new Info(TokenUtils.getAttribute(request));
            return info;
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
