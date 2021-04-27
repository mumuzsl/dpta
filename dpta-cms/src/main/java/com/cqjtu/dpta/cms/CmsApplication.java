package com.cqjtu.dpta.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: mumu
 * date: 2021/4/23
 */
@SpringBootApplication(scanBasePackages = "com.cqjtu")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
