package com.cqjtu.dpta.web.support;

import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.web.security.MinUser;
import com.cqjtu.dpta.web.security.UserCheck;
import com.cqjtu.dpta.web.security.UserCheckException;
import com.cqjtu.dpta.web.security.UserChecker;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/4/25
 */
@Component
public class UserIdChecker implements UserChecker {
    @Resource
    private DistrUserService distrUserService;

    @Resource
    private OrderService orderService;

    @Override
    public void check(MinUser minUser, Map<String, Object> map) {
        Object id = map.get("id");

        Long distrId = orderService.getDistrId((Long) id);

        if (minUser.getId() != distrId) {
            throw new UserCheckException();
        }
    }
}
