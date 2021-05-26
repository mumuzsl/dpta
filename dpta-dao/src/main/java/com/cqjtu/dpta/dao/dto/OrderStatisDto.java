package com.cqjtu.dpta.dao.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/10
 */
@Data
public class OrderStatisDto {
    private Integer state;
    private Integer count;
    private BigDecimal sum;
    private LocalDateTime maxTime;
    private LocalDateTime minTime;
}

