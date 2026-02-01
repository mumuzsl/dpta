package com.cqjtu.dpta.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ServiceCheckController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/service/{serviceName}")
    public Mono<String> testService(@PathVariable("serviceName") String serviceName) {
        return webClientBuilder.build()
                .get()
                .uri("http://" + serviceName + "/actuator/health")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/services")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    @GetMapping("/instances")
    public List<ServiceInstance> getInstances(@RequestParam("serviceId") String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }
}
