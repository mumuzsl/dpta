package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 平台-商品表
 * </p>
 *
 * @author mumu
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_paf_comm")
public class PafComm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编码
     */
    @TableId(value = "COMM_ID", type = IdType.INPUT)
    private Long commId;

    /**
     * 商品名称
     */
    @TableField("COMM_NM")
    private String commNm;

    /**
     * 类型
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 供应商编码
     */
    @TableField("SUPP_ID")
    private Long suppId;

    /**
     * 销售量
     */
    @TableField("SALES_VOLUME")
    private Integer salesVolume;

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

    /**
     * 退款规则
     */
    @TableField("REFUND_ID")
    private Long refundId;

    /**
     * 上下架状态
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 图片地址
     */
    @TableField("IMG_URL")
    private String imgUrl;

    private BigDecimal suppPrice;
}
