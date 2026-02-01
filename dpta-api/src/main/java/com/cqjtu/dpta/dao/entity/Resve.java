package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 预备金表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_resve")
public class Resve implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分销商编码
     */
    @TableId("DISTR_ID")
    private Long distrId;

    /**
     * 预备金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 更新时间
     */
    @TableField("UDT_TM")
    private LocalDateTime udtTm;


}
