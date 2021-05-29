package com.cqjtu.dpta.config;


import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.cqjtu.dpta.auto.AutoCheck;
import com.cqjtu.dpta.auto.AutoCreate;
import com.cqjtu.dpta.auto.BuildEs;
import com.cqjtu.dpta.common.config.DptaProperties;
import com.cqjtu.dpta.common.vo.AliPayBean;
import com.cqjtu.dpta.web.security.DefaultUserChecker;
import com.cqjtu.dpta.web.security.UserChecker;
import com.cqjtu.dpta.web.support.OrderRedisSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;


/**
 * @author mumu
 * @date 2021/3/4 19:28
 */
@Configuration
@EnableConfigurationProperties(DptaProperties.class)
public class DptaConfiguration {

    @ConditionalOnProperty(prefix = "dpta", name = "auto-gen", havingValue = "true")
    @Bean
    public AutoCreate autoCreate() {
        return new AutoCreate();
    }

    @ConditionalOnProperty(prefix = "dpta", name = "build-es", havingValue = "true")
    @Bean
    public BuildEs buildEs() {
        return new BuildEs();
    }

    @ConditionalOnProperty(prefix = "dpta", name = "auto-check", havingValue = "true")
    @Bean
    public AutoCheck autoCheck() {
        return new AutoCheck();
    }

    @Bean
    public OrderRedisSupport orderRedisSupport() {
        return new OrderRedisSupport();
    }

    @Bean
    public UserChecker defaultUserChecker() {
        return new DefaultUserChecker();
    }

    @Bean
    public AlipayClient getClient(AliPayBean aliPayBean) throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(aliPayBean.getServerUrl());  //gateway:支付宝网关（固定）https://openapi.alipay.com/gateway.do
        certAlipayRequest.setAppId(aliPayBean.getAppId());  //APPID 即创建应用后生成,详情见创建应用并获取 APPID
        certAlipayRequest.setPrivateKey(aliPayBean.getPrivateKey());  //开发者应用私钥，由开发者自己生成
        certAlipayRequest.setFormat(aliPayBean.getFormat());  //参数返回格式，只支持 json 格式
        certAlipayRequest.setCharset(aliPayBean.getCharset());  //请求和签名使用的字符编码格式，支持 GBK和 UTF-8
        certAlipayRequest.setSignType(aliPayBean.getSignType());  //商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐商家使用 RSA2。
        certAlipayRequest.setCertPath(aliPayBean.getAppCertPath()); //应用公钥证书路径（app_cert_path 文件绝对路径）
        certAlipayRequest.setAlipayPublicCertPath(aliPayBean.getAlipayCertPath()); //支付宝公钥证书文件路径（alipay_cert_path 文件绝对路径）
        certAlipayRequest.setRootCertPath(aliPayBean.getAlipayRootCertPath());  //支付宝CA根证书文件路径（alipay_root_cert_path 文件绝对路径）
        return new DefaultAlipayClient(certAlipayRequest);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().forEach(httpMessageConverter -> {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) httpMessageConverter;
                stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
            }
        });

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);
        return restTemplate;
    }

}
