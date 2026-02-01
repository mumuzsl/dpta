package com.cqjtu.dpta.web.controller.api.open;

import com.cqjtu.dpta.service.api.DistrUserService;
import com.cqjtu.dpta.service.api.UserService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.TokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: mumu
 * date: 2021/5/23
 */
@RestController
@RequestMapping
public class IndexOpenApi {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserService userService;
    @Resource
    private DistrUserService distrUserService;

    @GetMapping("logout")
    public Result logout(HttpServletResponse response) {
        TokenUtils.clearHeader(response);
        return Result.ok();
    }
}
