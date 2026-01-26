package com.cqjtu.dpta.web.security.password;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: mumu
 * @date: 2026/1/25 14:27
 */
public class PasswordAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!AuthorizationGrantType.PASSWORD.getValue().equals(grantType)) {
            return null;
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        String scope = request.getParameter(OAuth2ParameterNames.SCOPE);
        Set<String> scopes = new HashSet<>(StrUtil.split(scope, ','));

        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) && !key.equals(OAuth2ParameterNames.REFRESH_TOKEN)
                    && !key.equals(OAuth2ParameterNames.SCOPE)) {
                additionalParameters.put(key, (value.length == 1) ? value[0] : value);
            }
        });


        return new PasswordAuthenticationToken(clientPrincipal, additionalParameters, scopes);
    }
}
