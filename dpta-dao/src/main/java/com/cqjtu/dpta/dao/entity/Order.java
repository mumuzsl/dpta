package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author mumu
 * @since 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    /**
     * 商铺编码
     */
    @TableField("SHOP_ID")
    private Long shopId;

    /**
     * 日期
     */
    @TableField(value = "DATM", fill = FieldFill.INSERT)
    private LocalDateTime datm;

    /**
     * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
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

    /**
     * 核销本金
     */
    @TableField("BASE_P")
    private BigDecimal baseP;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 买家电话
     */
    private String phone;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 快递费
     */
    private BigDecimal expressMoney;

    /**
     * 快递单号
     */
    private String expressNo;


}
