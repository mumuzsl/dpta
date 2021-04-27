package com.cqjtu.dpta.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * author: mumu
 * date: 2021/4/13
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .groupName("api")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/api/.*"))
//                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    @Bean
    public Docket AdminConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .groupName("admin")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/admin/.*"))
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("网站-API文档")
                .description("本文档描述了网站微服务接口定义")
                .version("1.0")
                .contact(new Contact("mumu", "http://www.cqjtu.edu.cn", "1445600106@qq.com"))
                .build();
    }
}
