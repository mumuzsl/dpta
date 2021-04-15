package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
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
    @TableId("D_RES_ID")
    private Long dResId;

    /**
     * 分销商编码
     */
    @TableField("DISTR_ID")
    private Long distrId;

    /**
     * 类型(1: 付款；2：充值；3：还款）
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
    @TableField("ORD_ID")
    private Long ordId;

    /**
     * 创建日期
     */
    @TableField("CREATE_TM")
    private LocalDateTime createTm;


}
