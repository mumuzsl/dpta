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
 * 预备金明细表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_resve_d")
public class ResveD implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流水编码
     */
    @TableId(value = "D_RES_ID", type = IdType.AUTO)
    private Long dResId;

    /**
     * 分销商编码
     */
    @TableField("DISTR_ID")
    private Long distrId;

    /**
     * 类型(1: 付款；2：还款；3：充值；4：提现；5：佣金划入）
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 预备金余额
     */
    @TableField("BALANCE")
    private BigDecimal balance;

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


}
