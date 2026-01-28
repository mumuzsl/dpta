package com.cqjtu.dpta.web.security.extend;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cqjtu.dpta.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/4/22
 */
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final ParameterizedTypeReference<Result<Map<String, Object>>> STRING_OBJECT_MAP = new ParameterizedTypeReference<>() {
    };

    private GenericHttpMessageConverter<Object> jsonMessageConverter = new FastJsonHttpMessageConverter();
    private Converter<OAuth2AccessTokenResponse, Map<String, Object>> accessTokenResponseParametersConverter = new DefaultOAuth2AccessTokenResponseMapConverter();

    public void writeInternal(OAuth2AccessTokenResponse tokenResponse, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
        try {
            Map<String, Object> tokenResponseParameters = (Map) this.accessTokenResponseParametersConverter.convert(tokenResponse);
            Result<Map<String, Object>> result = Result.ok(tokenResponseParameters);
            this.jsonMessageConverter.write(result, STRING_OBJECT_MAP.getType(), MediaType.APPLICATION_JSON, outputMessage);
        } catch (Exception ex) {
            throw new HttpMessageNotWritableException("An error occurred writing the OAuth 2.0 Access Token Response: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (!(authentication instanceof OAuth2AccessTokenAuthenticationToken accessTokenAuthentication)) {
            if (log.isErrorEnabled()) {
                log.error(Authentication.class.getSimpleName() + " must be of type "
                        + OAuth2AccessTokenAuthenticationToken.class.getName() + " but was "
                        + authentication.getClass().getName());
            }
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "Unable to process the access token response.", null);
            throw new OAuth2AuthenticationException(error);
        }

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType())
                .scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }

        OAuth2AccessTokenResponse accessTokenResponse = builder.build();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        writeInternal(accessTokenResponse, httpResponse);
    }

}
