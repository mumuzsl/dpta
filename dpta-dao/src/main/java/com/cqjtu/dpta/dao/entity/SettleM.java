package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 佣金月结算记录表
 * </p>
 *
 * @author nisu
 * @since 2021-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_settle_m")
public class SettleM implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 分销商编码
     */
    @TableField("DISTR_ID")
    private Long distrId;

    /**
     * 佣金
     */
    @TableField("COM_P")
    private BigDecimal comP;

    /**
     * 税金
     */
    @TableField("TAX_P")
    private BigDecimal taxP;

    /**
     * 平台分润（未扣税）
     */
    @TableField("PLA_P")
    private BigDecimal plaP;

    /**
     * 本金
     */
    @TableField("BAS_P")
    private BigDecimal basP;

    /**
     * 结算日期
     */
    @TableField("SETTLE_TM")
    private LocalDateTime settleTm;


}
