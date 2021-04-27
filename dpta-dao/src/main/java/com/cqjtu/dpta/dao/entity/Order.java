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
 * 订单表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 商铺编码
     */
    @TableField("SHOP_ID")
    private Long shopId;

    /**
     * 日期
     */
    @TableField(value = "DATM")
    private LocalDateTime datm;

    /**
     * 核销状态
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 分润金额
     */
    @TableField("PROFIT")
    private BigDecimal profit;

    /**
     * 核销日期
     */
    @TableField("VERIFY_TM")
    private LocalDateTime verifyTm;

    /**
     * 总金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;


}
