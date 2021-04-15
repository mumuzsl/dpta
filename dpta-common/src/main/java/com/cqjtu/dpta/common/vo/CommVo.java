package com.cqjtu.dpta.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * author: mumu
 * date: 2021/4/13
 */
@Data
public class CommVo {

    private Long commId;

    private String commName;

    private BigDecimal maxPrice;

    private BigDecimal minPrice;

    private BigDecimal avgPrice;

    private Integer count;

    private BigDecimal amount;

}
