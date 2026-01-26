package com.cqjtu.dpta.web.security.extend;

import com.cqjtu.dpta.web.support.BigUser;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;

/**
 * @author: mumu
 * @date: 2026/1/25 00:24
 */
@Component
public class MyOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    @Resource
    OAuth2AuthorizationService oAuth2AuthorizationService;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2Authorization oldAuthorization = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (Objects.isNull(oldAuthorization)) {
            throw new InvalidBearerTokenException(token);
        }

        Object principal = oldAuthorization.getAttribute(Principal.class.getName());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        BigUser bigUser = (BigUser) usernamePasswordAuthenticationToken.getPrincipal();

        return bigUser;
    }
}
