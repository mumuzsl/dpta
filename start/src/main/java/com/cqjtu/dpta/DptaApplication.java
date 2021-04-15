package com.cqjtu.dpta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author mumu
 * @date 2021/3/3 22:28
 */
@SpringBootApplication(scanBasePackages = {"com.cqjtu"})
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
