package ltd.newbee.mall.controller.mall;

import cn.hutool.core.util.StrUtil;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/5/5
 */
@RestController
public class MyController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/platform/**")
    public Result get(HttpServletRequest request) {
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> list = new ArrayList<>(parameterMap.size());
        parameterMap.forEach((name, values) -> {
            list.add(name + '=' + values[0]);
        });
        Result result = restTemplate.getForObject("http://localhost:8080" + uri + "?" + StrUtil.join("&", list), Result.class);
        return result;
    }

    @PostMapping("/platform/**")
    public Result post(@RequestBody Object object,
                       HttpServletRequest request) {
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> list = new ArrayList<>(parameterMap.size());
        parameterMap.forEach((name, values) -> {
            list.add(name + '=' + values[0]);
        });
        Result result = restTemplate.postForObject("http://localhost:8080" + uri + "?" + StrUtil.join("&", list), object, Result.class);
        return result;
    }

}
