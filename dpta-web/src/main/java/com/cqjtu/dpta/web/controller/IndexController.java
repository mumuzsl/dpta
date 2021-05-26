package com.cqjtu.dpta.web.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.web.support.excel.LocalDateTimeConverter;
import com.cqjtu.dpta.web.support.excel.OrderData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * author: mumu
 * date: 2021/5/9
 */
@Controller
@RequestMapping
public class IndexController {

    @Resource
    private OrderService orderService;

    @GetMapping("/distr/api/download/excel")
    public void distrDownload(@RequestParam(required = false) String filename,
                              @RequestParam(required = false) Integer year,
                              @RequestParam(required = false) Integer month,
                              Info info,
                              HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        if (StrUtil.isBlank(filename)) {
            filename = LocalDateTimeUtil.formatNormal(LocalDate.now());
        }
        String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        if (year == null) {
            year = LocalDate.now().getYear();
        }
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        List<Order> orders = orderService.getMonth(info.id(), year, month);

        EasyExcel.write(response.getOutputStream(), OrderData.class)
                .sheet()
                .registerConverter(new LocalDateTimeConverter())
                .doWrite(orders);
    }

    @GetMapping("/platform/api/download/excel")
    public void platformDownload(@RequestParam(required = false) String filename,
                                 @RequestParam(required = false) Integer year,
                                 @RequestParam(required = false) Integer month,
                                 HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        if (StrUtil.isBlank(filename)) {
            filename = LocalDateTimeUtil.formatNormal(LocalDate.now());
        }
        String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        if (year == null) {
            year = LocalDate.now().getYear();
        }
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        List<Order> orders = orderService.getMonth(null, year, month);

        EasyExcel.write(response.getOutputStream(), OrderData.class)
                .sheet()
                .registerConverter(new LocalDateTimeConverter())
                .doWrite(orders);
    }


}
