package com.cqjtu.dpta.web.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice;
import com.cqjtu.dpta.web.support.DistrUserInterceptor;
import com.cqjtu.dpta.web.security.UniqueUserHandlerMethodArgumentResolver;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * @author mumu
 * @date 2021/3/4 17:54
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public JSONPResponseBodyAdvice jsonpResponseBodyAdvice() {
        return new JSONPResponseBodyAdvice();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        // 分销商商城
//        registry.addViewController("/").setViewName("mall/index");
//        registry.addViewController("/index").setViewName("mall/index");
//        registry.addViewController("/login").setViewName("mall/login");
//        registry.addViewController("/register").setViewName("mall/register");
//
//        // 管理员
//        registry.addViewController("/admin").setViewName("admin/index");
////        registry.addViewController("/admin/**").setViewName("admin/index");
//
//        // 分销商管理
//        registry.addViewController("/Fxsadmin").setViewName("fxsadmin/index");
//
//        // 错误页
//        registry.addViewController("/error/global").setViewName("error/error");
//        registry.addViewController("/error/500").setViewName("error/error_5xx");
//        registry.addViewController("/error/400").setViewName("error/error_400");
//        registry.addViewController("/error/404").setViewName("error/error_404");
//        registry.addRedirectViewController("/error", "/error/global");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DistrUserInterceptor()).addPathPatterns("/api/order");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UniqueUserHandlerMethodArgumentResolver());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = new FastJsonConfig();

//        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(0, converter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new ErrorPageRegistrarImpl();
    }

    static class ErrorPageRegistrarImpl implements ErrorPageRegistrar {
        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            ErrorPage globalErrorPage = new ErrorPage("/error/global");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/5xx");
            ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
            registry.addErrorPages(errorPage400, errorPage404, errorPage500, globalErrorPage);
        }
    }
}

