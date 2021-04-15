package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商铺-商品表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_shp_comm")
public class ShpComm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编码
     */
    @TableId("COMM_ID")
    private Long commId;

    /**
     * 商铺编码
     */
    @TableField("SHOP_ID")
    private Long shopId;

    /**
     * 售价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField("STOCK")
    private Integer stock;

    /**
     * 状态
     */
    @TableField("STATE")
    private Integer state;


}
