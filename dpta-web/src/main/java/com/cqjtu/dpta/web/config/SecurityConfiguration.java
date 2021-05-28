package com.cqjtu.dpta.web.config;

import com.cqjtu.dpta.web.filter.OptionsRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static class Support extends WebSecurityConfigurerAdapter {
        UserDetailsService userDetailsService;
        PasswordEncoder passwordEncoder;

        public Support(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.cors();

//            http.logout(logout ->
//                    logout
//                            .addLogoutHandler(TokenUtils.tokenLogoutHandler())
//                            .logoutRequestMatcher(new AntPathRequestMatcher("logout", "GET"))
//                            .permitAll()
//            );

            customConfigure(http);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/**");
        }

        protected void customConfigure(HttpSecurity http) throws Exception {
        }

        protected <T> T getBean(Class<T> requiredType) {
            return getApplicationContext().getBean(requiredType);
        }
    }

    //    @Configuration
    public static class DistrSecurityConfiguration extends Support {
        public DistrSecurityConfiguration(UserDetailsService distrUserDetailsServiceImpl, PasswordEncoder passwordEncoder) {
            super(distrUserDetailsServiceImpl, passwordEncoder);
        }

        @Override
        protected void customConfigure(HttpSecurity http) throws Exception {
            http.antMatcher("/distr/**");

            http.addFilterAfter(new OptionsRequestFilter(), CorsFilter.class);

            http.authorizeRequests(authorizeRequests ->
                    authorizeRequests
                            .anyRequest().hasRole("DISTR")
            );


//            http.formLogin(formLogin ->
//                    formLogin
//                            .loginPage("/distr/login")
//                            .loginProcessingUrl("/distr/login")
//                            .permitAll()
//            );
        }
    }

    @Configuration
    @Order(1)
    public static class AdminWebSecurityConfigurer extends Support {
        public AdminWebSecurityConfigurer(UserDetailsService adminUserDetailsServiceImpl, PasswordEncoder passwordEncoder) {
            super(adminUserDetailsServiceImpl, passwordEncoder);
        }

        @Override
        protected void customConfigure(HttpSecurity http) throws Exception {
//            http.antMatcher("/platform/**");
//
//            http.authorizeRequests(authorizeRequests ->
//                    authorizeRequests
//                            .anyRequest().hasRole("ADMIN")
//            );
//
//            http.formLogin(formLogin ->
//                    formLogin
//                            .loginPage("/admin/login")
//                            .loginProcessingUrl("/platform/login")
//                            .permitAll()
//            );
        }
    }

}
