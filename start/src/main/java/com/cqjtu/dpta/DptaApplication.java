package com.cqjtu.dpta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author mumu
 * @date 2021/3/3 22:28
 */
@SpringBootApplication
@EnableScheduling
public class DptaApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DptaApplication.class, args);
//        printBeans(run);
    }

    public static void printBeans(ConfigurableApplicationContext run) {
        for (String name : run.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
