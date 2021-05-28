package ltd.newbee.mall.controller.mall;

import cn.hutool.core.util.StrUtil;
import com.cqjtu.dpta.common.result.Result;
import ltd.newbee.mall.config.DptaProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Resource
    private DptaProperties dptaProperties;

    @GetMapping("/platform/**")
    public Result get(HttpServletRequest request,
                      HttpSession session) {
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> list = new ArrayList<>(parameterMap.size());
        parameterMap.forEach((name, values) -> {
            list.add(name + '=' + values[0]);
        });
        list.add("token=" + session.getAttribute("token"));
        Result result = restTemplate.getForObject(dptaProperties.getUrl() + uri + "?" + StrUtil.join("&", list), Result.class);
        return result;
    }

    @PostMapping("/platform/**")
    public Result post(@RequestBody(required = false) Object object,
                       HttpServletRequest request,
                       HttpSession session) {
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> list = new ArrayList<>(parameterMap.size());
        parameterMap.forEach((name, values) -> {
            list.add(name + '=' + values[0]);
        });
        list.add("token=" + session.getAttribute("token"));
        Result result = restTemplate.postForObject(dptaProperties.getUrl() + uri + "?" + StrUtil.join("&", list), object, Result.class);
        return result;
    }

}
