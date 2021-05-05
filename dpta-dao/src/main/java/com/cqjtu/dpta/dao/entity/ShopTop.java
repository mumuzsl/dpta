package com.cqjtu.dpta.dao.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ly
 * @date 2021-05-01 22:10
 */
@Data
public class ShopTop {

    private String commName;

    private String spec;

    private BigDecimal rePrice;

    private Integer saVolume;
}
