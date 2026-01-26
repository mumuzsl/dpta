package com.cqjtu.dpta;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author mumu
 * @date 2021/3/3 22:28
 */
@SpringBootApplication(scanBasePackages = "com.cqjtu.dpta")
@EnableSpringUtil
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
