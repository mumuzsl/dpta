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
 * 佣金规则表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_comm_r")
public class CommR implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 佣金规则编码
     */
    @TableId(value = "R_COMM_ID", type = IdType.AUTO)
    private Long rCommId;

    /**
     * 规则名称
     */
    @TableField("R_COMM_NM")
    private String rCommNm;

    /**
     * 规则状态，0是禁用，1是启用
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 规则类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 分润值
     */
    @TableField("VALUE")
    private BigDecimal value;

    @TableField("DELETED")
    private int deleted;


}
