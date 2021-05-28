package com.cqjtu.dpta.common.web;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单时，后台接收数据的类
 * author: mumu
 * date: 2021/4/22
 */
@Data
public class OrderParam {
    private Long id;

    private Long shopId;

    private List<CommParam> comms;

    private String address;

    private String phone;

    private String receiver;

    private BigDecimal expressMoney;

    private String expressNo;
}
