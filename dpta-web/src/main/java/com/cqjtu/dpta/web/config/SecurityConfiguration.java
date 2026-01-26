package com.cqjtu.dpta.web.config;

import com.cqjtu.dpta.web.security.extend.*;
import com.cqjtu.dpta.web.security.password.PasswordAuthenticationConverter;
import com.cqjtu.dpta.web.security.password.PasswordAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    MyOpaqueTokenIntrospector myOpaqueTokenIntrospector;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/oauth2/**")
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new MyAuthenticationEntryPoint()))
                .with(OAuth2AuthorizationServerConfigurer.authorizationServer(),
                        authorizationServer ->
                                authorizationServer.tokenEndpoint(tokenEndpoint
                                        -> tokenEndpoint.accessTokenRequestConverter(new PasswordAuthenticationConverter())
                                        // .accessTokenResponseHandler(new MyAuthenticationSuccessHandler())
                                        .errorResponseHandler(new MyAuthenticationFailureHandler())
                                )
                )
                .csrf(AbstractHttpConfigurer::disable);


        DefaultSecurityFilterChain securityFilterChain = http.build();

        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.authenticationProvider(new MyUserDetailsAuthenticationProvider());
        http.authenticationProvider(new PasswordAuthenticationProvider(new MyOAuth2AccessTokenGenerator(), authorizationService, authenticationManager));

        return securityFilterChain;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // .securityMatcher("/distr/**")
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new MyAuthenticationEntryPoint()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(opaque ->
                        opaque.introspector(myOpaqueTokenIntrospector)
                ));

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient adminClient = RegisteredClient.withId("admin")
                .clientId("admin")
                .clientSecret(passwordEncoder().encode("admin"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // 支持客户端凭证模式
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                // .redirectUri("http://127.0.0.1:8080/login/oauth2/code/my-client-oidc")
                .redirectUri("/authorized")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .scope("write")
                .clientSettings(ClientSettings.builder().requireProofKey(false).requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE) // ✅ 发短 token
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();

        RegisteredClient distrUserClient = RegisteredClient.withId("distr_user")
                .clientId("distr_user")
                .clientSecret(passwordEncoder().encode("distr_user"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // 支持客户端凭证模式
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                // .redirectUri("http://127.0.0.1:8080/login/oauth2/code/my-client-oidc")
                .redirectUri("/authorized")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .scope("write")
                .clientSettings(ClientSettings.builder().requireProofKey(false).requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE) // ✅ 发短 token
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(adminClient, distrUserClient);
    }
}
