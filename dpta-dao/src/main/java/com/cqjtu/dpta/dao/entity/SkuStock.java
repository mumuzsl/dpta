package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sku_stock")
public class SkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 商铺商品id
     */
    private Long shopCommId;

    /**
     * 平台商品id
     */
    private Long pafCommId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 已锁定库存
     */
    private Integer lockedStock;

    /**
     * 版本，用于乐观锁
     */
    private Integer version;


}
