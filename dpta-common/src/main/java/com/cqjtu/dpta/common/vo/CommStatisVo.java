package com.cqjtu.dpta.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品统计类
 * author: mumu
 * date: 2021/4/13
 */
@Data
public class CommStatisVo {

    private Long commId;

    private String commName;

    private BigDecimal maxPrice;

    private BigDecimal minPrice;

    private BigDecimal avgPrice;

    private Integer count;

    private BigDecimal amount;

}
