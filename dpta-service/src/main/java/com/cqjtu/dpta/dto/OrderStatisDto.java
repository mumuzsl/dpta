package com.cqjtu.dpta.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/10
 */
@Data
public class OrderStatisDto {
    private Integer state;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate day;
    private Integer count;
    private BigDecimal sum;
    private LocalDateTime maxTime;
    private LocalDateTime minTime;
}

