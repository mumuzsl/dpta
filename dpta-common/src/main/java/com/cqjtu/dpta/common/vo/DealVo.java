package com.cqjtu.dpta.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DealVo {
    private Long dealId;
    private String commNm;
    private BigDecimal price;
    private int count;
    private LocalDateTime createTime;
}
