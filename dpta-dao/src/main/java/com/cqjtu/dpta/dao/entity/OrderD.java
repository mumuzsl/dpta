package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order_d")
public class OrderD implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 单价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 订单编码
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 商品编码
     */
    @TableField("COMM_ID")
    private Long commId;

    /**
     * 数量
     */
    @TableField("COUNT")
    private Integer count;

    /**
     * 分润金额
     */
    @TableField("PROFIT")
    private BigDecimal profit;


    private Long skuId;
}
