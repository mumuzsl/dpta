package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 授信表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_credit")
public class Credit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 授信编码
     */
    @TableId(value = "CREDIT_ID",type = IdType.AUTO)
    private Long creditId;

    /**
     * 供应商编码
     */
    @TableField("SUPP_ID")
    private Long suppId;

    @TableField("DISTR_ID")
    private Long distrId;

    /**
     * 授信金额
     */
    @TableField("CREDIT_AMOUT")
    private BigDecimal creditAmout;

    /**
     * 已用金额
     */
    @TableField("USED_AMOUT")
    private BigDecimal usedAmout;

    /**
     * 状态
     */
    @TableField("STATE")
    private Integer state;

    @TableField(exist = false)
    private BigDecimal enCredit;
}
