package com.cqjtu.dpta.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 创建订单时，后台接收数据的类
 * author: mumu
 * date: 2021/4/22
 */
@Data
public class OrderVo {

    private Long shopId;

    private List<CommVo> comms;

}
