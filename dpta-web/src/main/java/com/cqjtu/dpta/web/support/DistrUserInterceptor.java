package com.cqjtu.dpta.web.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Optional;

/**
 * author: mumu
 * date: 2021/4/15
 */
public class DistrUserInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private DistrUserService distrUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        Optional.of(authentication)
//                .map(Authentication::getDetails)
//                .filter(o -> o instanceof UserDetails)
//                .map(this::getDistrUser)
//                .map(DistrUser::getId)
//        ;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    private DistrUser getDistrUser(Object o) {
        UserDetails ud = (UserDetails) o;
        String username = ud.getUsername();
        return distrUserService.getOne(getQueryWrapper(username));
    }

    private static QueryWrapper<DistrUser> getQueryWrapper(String username) {
        return new QueryWrapper<DistrUser>().eq("username", username);
    }
}
