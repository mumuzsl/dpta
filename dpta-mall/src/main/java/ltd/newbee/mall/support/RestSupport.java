package ltd.newbee.mall.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqjtu.dpta.common.result.Result;
import ltd.newbee.mall.config.DptaProperties;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public class RestSupport {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DptaProperties dptaProperties;

    public static <T> TypeReference<Result<T>> buildType(Class<T> clazz) {
        return new TypeReference<com.cqjtu.dpta.common.result.Result<T>>(clazz) {
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
