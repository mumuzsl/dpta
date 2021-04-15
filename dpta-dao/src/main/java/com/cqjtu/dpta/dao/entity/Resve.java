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
