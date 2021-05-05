package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分销商等级表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_distr_level")
public class DistrLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 等级编码
     */
    @TableId(value = "LEVEL_ID", type = IdType.AUTO)
    private Long levelId;

    /**
     * 等级名称
     */
    @TableField("LEVEL_NM")
    private String levelNm;

    /**
     * 收益率
     */
    @TableField("YIELD")
    private BigDecimal yield;

    @TableField("SettledM")
    private Integer settledM;

    @TableField("PafP")
    private  BigDecimal pafP;

    @TableField("UpdateTm")
    private LocalDateTime updateTm;
}
