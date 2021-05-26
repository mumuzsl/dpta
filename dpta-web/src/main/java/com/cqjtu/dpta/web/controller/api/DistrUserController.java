package com.cqjtu.dpta.web.controller.api;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.api.DistrLevelService;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.TokenUtils;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.common.web.LoginParam;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.dao.entity.DistrUser;
import com.cqjtu.dpta.web.support.LoginSupport;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.TokenUtils;
import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.web.support.BigUser;
import com.cqjtu.dpta.common.web.LoginParam;
import com.cqjtu.dpta.common.web.Info;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class DistrUserController extends LoginSupport {

    @Resource
    private DistrUserService distrUserService;
    @Resource
    private DistrService distrService;
    @Resource
    private DistrLevelService distrLevelService;

    @Resource(name = "distrUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @GetMapping("api/detail")
    public Result detail(Info info) {
        Distr distr = distrService
                .lambdaQuery()
                .eq(Distr::getDistrId, info.id())
                .one();
        return Result.ok(distr);
    }

    @GetMapping("getByNm")
    public DistrUser getByNm(@RequestParam String name) {
        QueryWrapper<DistrUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",name);
        return distrUserService.getOne(wrapper);
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
        if (distrUser == null) {
            return Result.fail("用户不存在");
        }

        Distr distr = distrService.getById(distrUser.getDistrId());

        Info info = new Info();
        info.setAvatar(distr.getDistrNm());
        info.setName(distrUser.getUsername());
        info.setRole("distr");

        return Result.ok(info);
    }

    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam,
                        HttpServletResponse response,
                        HttpServletRequest request) {
        return postLogin(loginParam, token -> {
            Cookie cookie = TokenUtils.cookie(token);
            response.addCookie(cookie);
        });
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Resource
    ResveService resveService;
    @PostMapping("register")
    public Result register(@RequestBody LoginParam loginParam,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        if (loginParam.isInvalid()) {
            return Result.build(ResultCodeEnum.USER_LOGIN_PARAM_ERROR);
        }

        String username = loginParam.getUsername();
        String password = loginParam.getPassword();

        if (!PhoneUtil.isMobile(username)) {
            return Result.build(ResultCodeEnum.USER_PHONE_ERROR);
        }

        DistrUser distrUser = distrUserService.lambdaQuery().eq(DistrUser::getUsername, username).one();
        if (distrUser != null) {
            return Result.build(ResultCodeEnum.USER_REGISTERED);
        }

        Distr distr = new Distr();
        distr.setPayPwd(defaultPayPwd());
        distr.setDistrNm(defaultName());
        distr.setState(Const.DISABLE);
        distr.setPhone(username);
        distr.setLevelId(defaultLevelId());
        distr.setSettledTm(LocalDateTime.now());
        distrService.save(distr);

        distrUser = new DistrUser();
        distrUser.setDistrId(distr.getDistrId());
        distrUser.setUsername(username);
        distrUser.setPassword(passwordEncoder().encode(password));

        Resve resve = new Resve();
        resve.setDistrId(distr.getDistrId());
        resve.setAmount(new BigDecimal(0));
        resve.setUdtTm(LocalDateTime.now());
        resveService.save(resve);
        boolean b = distrUserService.save(distrUser);
        return Result.judge(b);
    }

    Long defaultLevelId() {
        return distrLevelService
                .lambdaQuery()
                .like(DistrLevel::getLevelNm, "一等")
                .one().getLevelId();
    }

    String defaultName() {
        return "分销商" + distrService.count();
    }

    String defaultPayPwd() {
        return passwordEncoder().encode("000000");
    }
}
