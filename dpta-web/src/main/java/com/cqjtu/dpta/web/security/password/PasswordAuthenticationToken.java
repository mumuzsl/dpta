package com.cqjtu.dpta.web.security.password;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

/**
 * @author: mumu
 * @date: 2026/1/25 14:23
 */
@Getter
public class PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    private final Set<String> scopes;

    protected PasswordAuthenticationToken(Authentication clientPrincipal, Map<String, Object> additionalParameters, Set<String> scopes) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = scopes;
    }
}
