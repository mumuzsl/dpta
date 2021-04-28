///**
// * 严肃声明：
// * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
// * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
// * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
// * Copyright (c) 2019-2020 十三 all rights reserved.
// * 版权所有，侵权必究！
// */
//package com.cqjtu.dpta.web.controller.admin;
//
//
//import com.cqjtu.dpta.api.UserService;
//import com.cqjtu.dpta.common.result.ServiceResultEnum;
//import com.cqjtu.dpta.dao.entity.User;
//import com.cqjtu.dpta.web.security.MinUser;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
///**
// * @author 13
// * @qq交流群 796794009
// * @email 2449207463@qq.com
// * @link https://github.com/newbee-ltd
// */
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Resource
//    private UserService userService;
//
//    @GetMapping({"/login"})
//    public String login() {
//        return "admin/login";
//    }
//
//    @GetMapping({"/test"})
//    public String test() {
//        return "admin/test";
//    }
//
//
//    @GetMapping({"", "/", "/index", "/index.html"})
//    public String index(HttpServletRequest request) {
//        request.setAttribute("path", "index");
//        return "admin/index";
//    }
//
////    @PostMapping(value = "/login")
////    public String login(@RequestParam("userName") String userName,
////                        @RequestParam("password") String password,
////                        @RequestParam("verifyCode") String verifyCode,
////                        HttpSession session) {
////        if (StringUtils.isEmpty(verifyCode)) {
////            session.setAttribute("errorMsg", "验证码不能为空");
////            return "admin/login";
////        }
////        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
////            session.setAttribute("errorMsg", "用户名或密码不能为空");
////            return "admin/login";
////        }
////        String kaptchaCode = session.getAttribute("verifyCode") + "";
////        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
////            session.setAttribute("errorMsg", "验证码错误");
////            return "admin/login";
////        }
////        AdminUser adminUser = adminUserService.login(userName, password);
////        if (adminUser != null) {
////            session.setAttribute("loginUser", adminUser.getNickName());
////            session.setAttribute("loginUserId", adminUser.getAdminUserId());
////            //session过期时间设置为7200秒 即两小时
////            //session.setMaxInactiveInterval(60 * 60 * 2);
////            return "redirect:/admin/index";
////        } else {
////            session.setAttribute("errorMsg", "登录失败");
////            return "admin/login";
////        }
////    }
//
//    @GetMapping("/profile")
//    public String profile(HttpServletRequest request, MinUser minUser) {
////        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
////        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
//        User user = userService.getById(minUser.longId());
//        if (user == null) {
//            return "admin/login";
//        }
//        request.setAttribute("path", "profile");
//        request.setAttribute("loginUserName", user.getUsername());
//        request.setAttribute("nickName", user.getUsername());
//        return "admin/profile";
//    }
//
//    @Resource
//    PasswordEncoder passwordEncoder;
//
//    @PostMapping("/profile/password")
//    @ResponseBody
//    public String passwordUpdate(HttpServletRequest request,
//                                 @RequestParam("originalPassword") String originalPassword,
//                                 @RequestParam("newPassword") String newPassword,
//                                 MinUser minUser) {
//        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
//            return "参数不能为空";
//        }
//        User user = userService.getById(minUser.longId());
//        if (user == null) {
//            return "用户不存在";
//        }
//        if (!passwordEncoder.matches(originalPassword, user.getPasswd())) {
//            return "原密码不正确";
//        }
//        user.setPasswd(passwordEncoder.encode(newPassword));
//        boolean update = userService
//                .lambdaUpdate()
//                .eq(User::getId, minUser.longId())
//                .update(user);
//        if (update) {
//            //修改成功后清空session中的数据，前端控制跳转至登录页
////            request.getSession().removeAttribute("loginUserId");
////            request.getSession().removeAttribute("loginUser");
////            request.getSession().removeAttribute("errorMsg");
//            return ServiceResultEnum.SUCCESS.getResult();
//        } else {
//            return "修改失败";
//        }
//    }
//
//    @PostMapping("/profile/name")
//    @ResponseBody
//    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
//                             @RequestParam("nickName") String nickName) {
//        return ServiceResultEnum.CLOSE.getResult();
//    }
//
////    @GetMapping("/logout")
////    public String logout(HttpServletRequest request) {
////        request.getSession().removeAttribute("loginUserId");
////        request.getSession().removeAttribute("loginUser");
////        request.getSession().removeAttribute("errorMsg");
////        return "admin/login";
////    }
//}
