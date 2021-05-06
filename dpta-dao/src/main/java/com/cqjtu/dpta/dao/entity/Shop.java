package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商铺表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商铺编码
     */
    @TableId(value = "SHOP_ID", type = IdType.AUTO)
    private Long shopId;

    /**
     * 商铺名称
     */
    @TableField("SHOP_NM")
    private String shopNm;

    /**
     * 分销商编码
     */
    @TableField("DISTR_ID")
    private Long distrId;

    /**
     * 状态
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 应用编码
     */
    @TableField("APP_ID")
    private Long appId;


}
