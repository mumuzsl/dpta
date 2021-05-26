package com.cqjtu.dpta.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PayM {
    private String dealId;
    private List<Credit> li;
    private BigDecimal amount;
    private BigDecimal resve;
    private BigDecimal enCredit;
    private BigDecimal useResve;
    private BigDecimal useCredit;
    private String passwd;
}
