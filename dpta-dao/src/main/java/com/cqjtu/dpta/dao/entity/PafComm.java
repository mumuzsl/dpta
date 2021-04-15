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
 * 平台-商品表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_paf_comm")
public class PafComm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编码
     */
    @TableId(value = "COMM_ID", type = IdType.AUTO)
    private Long commId;

    /**
     * 商品名称
     */
    @TableField("COMM_NM")
    private String commNm;

    /**
     * 类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 供应商编码
     */
    @TableField("SUPP_ID")
    private Long suppId;

    /**
     * 供货价
     */
    @TableField("SUPP_PRICE")
    private BigDecimal suppPrice;

    /**
     * 推荐售价
     */
    @TableField("RECOM_PRICE")
    private BigDecimal recomPrice;

    /**
     * 销售量
     */
    @TableField("SALES_VOLUME")
    private Integer salesVolume;

    /**
     * 库存
     */
    @TableField("STOCK")
    private Integer stock;

    /**
     * 商品详情
     */
    @TableField("COMM_D")
    private String commD;

    /**
     * 佣金规则编码
     */
    @TableField("R_COMM_ID")
    private Long rCommId;

    @TableField("REFUND_ID")
    private Long refundId;

    /**
     * 上下架状态，0是下架，1是上架
     */
    @TableField("STATE")
    private Integer state;
}
