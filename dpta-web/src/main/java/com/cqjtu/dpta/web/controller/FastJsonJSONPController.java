package com.cqjtu.dpta.web.controller;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

/**
 * author: mumu
 * date: 2021/4/20
 */
@ResponseJSONP
@Controller
@RequestMapping("jsonp")
@CrossOrigin
public class FastJsonJSONPController {
    private String[] urls = {"https://www.baidu.com", "https://www.bilibili.com"};

    @ResponseJSONP(callback = "callback")
    @RequestMapping("test")
    public Object test() {
        int index = new Random().nextInt(2);
        return urls[index];
    }

}
