package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 税费规则表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tax_r")
public class TaxR implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 税费规则编码
     */
    @TableId("TAX_ID")
    private Long taxId;

    /**
     * 起征点
     */
    @TableField("P_START")
    private BigDecimal pStart;

    /**
     * 征税上限
     */
    @TableField("P_END")
    private BigDecimal pEnd;

    /**
     * 起征比例
     */
    @TableField("RATE")
    private BigDecimal rate;

    /**
     * 速算扣除数
     */
    @TableField("DEDUCTION")
    private BigDecimal deduction;
}
