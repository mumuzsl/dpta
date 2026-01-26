package com.cqjtu.dpta.web.security.extend;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.cqjtu.dpta.web.service.AbstractUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Optional;

/**
 * @author: mumu
 * @date: 2026/1/25 15:59
 */
public class MyUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final static BasicAuthenticationConverter basicConvert = new BasicAuthenticationConverter();


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!StrUtil.equals(presentedPassword, userDetails.getPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(req -> req instanceof ServletRequestAttributes)
                .map(req -> (ServletRequestAttributes) req)
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new InternalAuthenticationServiceException("无法获取认证参数"));

        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        String clientId = request.getParameter(OAuth2ParameterNames.CLIENT_ID);

        if (StrUtil.isBlank(clientId)) {
            clientId = basicConvert.convert(request).getName();
        }

        Map<String, UserDetailsService> beansOfType = SpringUtil.getBeansOfType(UserDetailsService.class);

        String finalClientId = clientId;
        Optional<UserDetailsService> optional = beansOfType.entrySet()
                .stream()
                .filter(e -> {
                    UserDetailsService userDetailsService = e.getValue();
                    if (userDetailsService instanceof AbstractUserDetailsService abstractUserDetailsService) {
                        return abstractUserDetailsService.support(finalClientId, grantType);
                    }
                    return false;
                })
                .findFirst().map(Map.Entry::getValue);

        if (optional.isPresent()) {
            return optional.get().loadUserByUsername(username);
        }

        throw new InternalAuthenticationServiceException("无法获取用户详情");
    }
}
