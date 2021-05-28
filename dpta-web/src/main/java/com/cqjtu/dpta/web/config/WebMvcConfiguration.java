package com.cqjtu.dpta.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice;
import com.cqjtu.dpta.web.filter.OptionsRequestFilter;
import com.cqjtu.dpta.web.filter.TokenFilter;
import com.cqjtu.dpta.web.security.InfoHandlerMethodArgumentResolver;
import com.cqjtu.dpta.web.security.UniqueUserHandlerMethodArgumentResolver;
import com.cqjtu.dpta.web.support.AdminInterceptor;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
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

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter(source));
        registrationBean.setOrder(tokenFilterFilterRegistrationBean().getOrder() - 1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilterFilterRegistrationBean() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter());
        registrationBean.addUrlPatterns("/distr/api/*");
//        registrationBean.addUrlPatterns("/platform/api/*");

        return registrationBean;
    }

    //    @Bean
    public FilterRegistrationBean<OptionsRequestFilter> optionsRequestFilterFilterRegistrationBean() {
        FilterRegistrationBean<OptionsRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OptionsRequestFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Resource
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/platform/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UniqueUserHandlerMethodArgumentResolver());
        resolvers.add(new InfoHandlerMethodArgumentResolver());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);

        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(0, converter);
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
}

