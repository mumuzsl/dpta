package com.cqjtu.dpta.common.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditVo {
    private Long creditId;
    private String suppNm;
    private String distrNm;
    private BigDecimal creditAmout;
    private BigDecimal usedAmout;
    private int state;
}
