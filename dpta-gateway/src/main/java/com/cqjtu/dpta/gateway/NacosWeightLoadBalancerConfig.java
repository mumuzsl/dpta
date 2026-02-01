package com.cqjtu.dpta.gateway;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

// @Configuration
@ConditionalOnClass(NacosDiscoveryProperties.class)
public class NacosWeightLoadBalancerConfig {
}