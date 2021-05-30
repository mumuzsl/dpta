package com.cqjtu.dpta.web.support.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/12
 */
@Data
@ColumnWidth(25)
public class OrderData {
    @ExcelProperty(value = "订单号", converter = IdConverter.class)
    private Long id;

    @ExcelProperty("商铺编码")
    private Long shopId;

    @ExcelProperty(value = "创建日期", converter = LocalDateTimeConverter.class)
    private LocalDateTime datm;

    @ExcelProperty(value = "状态", converter = StateConverter.class)
    private Integer state;

    @ExcelProperty("分润金额")
    private BigDecimal profit;

    @ExcelIgnore
    private LocalDateTime verifyTm;

    /**
     * 核销本金
     */
    @ExcelIgnore
    private BigDecimal baseP;

    @ExcelProperty(value = "总金额", converter = MoneyConverter.class)
    private BigDecimal amount;

    @ExcelIgnore
    private String address;

    @ExcelProperty(value = "支付时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime payTime;

    @ExcelIgnore
    private String phone;

    @ExcelIgnore
    private String receiver;

    @ExcelIgnore
    private Integer deleted;

    @ExcelIgnore
    private Integer version;

    @ExcelIgnore
    private LocalDateTime updateTime;

    @ExcelIgnore
    private BigDecimal expressMoney;

    @ExcelIgnore
    private String expressNo;
}

