package ltd.newbee.mall.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.cqjtu.dpta.common.result.Result;
import jakarta.annotation.Resource;
import ltd.newbee.mall.config.DptaProperties;
import org.springframework.web.client.RestTemplate;

public class RestSupport {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DptaProperties dptaProperties;

    public static <T> TypeReference<Result<T>> buildType(Class<T> clazz) {
        return new TypeReference<Result<T>>(clazz) {
        };
    }

    public <T> com.cqjtu.dpta.common.result.Result<T> postForObject(String path, Object request, Class<T> clazz) {
        String json = restTemplate
                .postForObject(dptaProperties.getUrl() + path,
                        request,
                        String.class);
        return JSON.parseObject(json, buildType(clazz));
    }

    public <T> com.cqjtu.dpta.common.result.Result<T> getForObject(String path, Class<T> clazz) {
        String json = restTemplate
                .getForObject(dptaProperties.getUrl() + path,
                        String.class);
        return JSON.parseObject(json, buildType(clazz));
    }
}
