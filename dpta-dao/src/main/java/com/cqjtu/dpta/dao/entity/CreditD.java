package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 授信明细表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_credit_d")
public class CreditD implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流水编码
     */
    @TableId(value = "D_CRE_ID", type = IdType.AUTO)
    private Long dCreId;

    /**
     * 授信编码
     */
    @TableField("CREDIT_ID")
    private Long creditId;

    /**
     * 类型（1：使用授信付款；2：还款）
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 订单编码
     */
    @TableField("DEAL_ID")
    private Long dealId;

    /**
     * 创建日期
     */
    @TableField("CREATE_TM")
    private LocalDateTime createTm;

    /**
     * 已用金额
     */
    @TableField("USED_AMOUNT")
    private BigDecimal usedAmount;


}
