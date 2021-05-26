package com.cqjtu.dpta.common.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * author: mumu
 * date: 2021/5/10
 */
@ConfigurationProperties(prefix = "dpta")
public class DptaProperties implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        MALL_URL = getMallUrl();
    }

    public static String MALL_URL = "";

    private String mallUrl = "";

    public String getMallUrl() {
        return mallUrl;
    }

    public void setMallUrl(String mallUrl) {
        this.mallUrl = mallUrl;
    }
}
