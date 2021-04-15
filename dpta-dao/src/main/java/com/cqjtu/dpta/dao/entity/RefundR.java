package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 退款规则表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_refund_r")
public class RefundR implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退款规则编码
     */
    @TableId("REFUND_ID")
    private Long refundId;

    /**
     * 规则名称
     */
    @TableField("REFUND_NM")
    private String refundNm;

    /**
     * 规则状态
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 规则类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 分润预留时间
     */
    @TableField("RESERVE_TM")
    private Integer reserveTm;

    /**
     * 退款条件
     */
    @TableField("CONDITION")
    private String condition;


}
