package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 商铺销售表
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sale")
public class Sale implements Serializable {

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
     * 单价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 日期
     */
    @TableField("DATM")
    private LocalDateTime datm;

    /**
     * 核销状态
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 分润金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;


}
