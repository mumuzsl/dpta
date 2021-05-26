package com.cqjtu.dpta.web.controller.api.admin;

import com.cqjtu.dpta.api.UserService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.TokenUtils;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.common.web.LoginParam;
import com.cqjtu.dpta.dao.entity.User;
import com.cqjtu.dpta.web.support.LoginSupport;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author: mumu
 * date: 2021/5/23
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends LoginSupport {
    @Resource(name = "adminUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource
    private UserService userService;

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @GetMapping("info")
    public Result info(String token) {
        String subject = TokenUtils.subject(token);
        User user = userService.getById(subject);

        if (user == null) {
            return Result.fail("用户不存在");
        }

        Info info = new Info();
        info.setAvatar(user.getUsername());
        info.setName(user.getUsername());
        info.setRole("admin");
        return Result.ok(info);
    }

    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam,
                        HttpServletResponse response,
                        HttpServletRequest request) {
        return loginedAddCookie(loginParam, response);
    }
}
