package com.cqjtu.cms.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * author: mumu
 * date: 2021/5/3
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
@Data
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private String host;
    private int port;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
    }

}
