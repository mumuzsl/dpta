package com.cqjtu.dpta.dao.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ly
 * @date 2021-05-09 18:20
 */

@Data
public class ShopPredict {
    private String name;
    private BigDecimal price;
    private Integer sale;
}
