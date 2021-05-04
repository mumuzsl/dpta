package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrUser;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.web.security.TokenUtils;
import com.cqjtu.dpta.web.support.BigUser;
import com.cqjtu.dpta.web.support.LoginParam;
import com.cqjtu.dpta.web.support.Info;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/distr")
public class DistrUserController {

    @Resource
    private DistrUserService distrUserService;
    @Resource
    private DistrService distrService;

    @Resource(name = "distrUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @GetMapping("api/detail")
    public Result detail(Info info) {
        Distr distr = distrService
                .lambdaQuery()
                .eq(Distr::getDistrId, info.longId())
                .one();
        return Result.ok(distr);
    }

    @GetMapping("logout")
    public Result logout(HttpServletResponse response) {
        TokenUtils.clearHeader(response);
        return Result.ok();
    }

    @GetMapping("info")
    public Result info(String token) {
        String subject = TokenUtils.subject(token);
        DistrUser distrUser = distrUserService.getById(subject);
        Info info = new Info();
        info.setAvatar(distrUser.getUsername());
        info.setName(distrUser.getUsername());
        return Result.ok(info);
    }

    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam,
                        HttpServletResponse response,
                        HttpServletRequest request) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();


        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.fail(ResultCodeEnum.USER_LOGIN_PARAM_ERROR);
        }

        BigUser distrUser = null;
        try {
            distrUser = (BigUser) userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, distrUser.getPassword())) {
                return Result.fail(ResultCodeEnum.USER_PASSWORD_NOT_MATCH);
            }
            String token = TokenUtils.create(distrUser.getId(), password);
            Info info = new Info(token);
            Cookie cookie = TokenUtils.cookie(token);
            response.addCookie(cookie);
            return Result.ok(info);
        } catch (UsernameNotFoundException e) {
            return Result.fail(ResultCodeEnum.USER_NOT_FOUNT_USER);
        }
    }

//    @GetMapping
//    public Result page(@PageableDefault Pageable pageable) {
//        IPage<DistrUser> page = distrUserService.page(pageable);
//        return Result.ok(page);
//    }
//
//    @PostMapping("modif")
//    public Result modif(@RequestBody DistrUser distrUser) {
//        boolean result = distrUserService.updateById(distrUser);
//        return Result.judge(result);
//    }
//
//    @PostMapping("add")
//    public Result add(@RequestBody DistrUser distrUser) {
//        boolean result = distrUserService.save(distrUser);
//        return Result.judge(result);
//    }
//
//    @PostMapping("del")
//    public Result del(@RequestBody List ids) {
//        boolean result = distrUserService.removeByIds(ids);
//        return Result.judge(result);
//    }

}
