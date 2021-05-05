package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分销商表
 * </p>
 *
 * @author mumu
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_distr")
public class Distr implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分销商编码
     */
    @TableId(value = "DISTR_ID", type = IdType.AUTO)
    private Long distrId;

    /**
     * 分销商名称
     */
    @TableField("DISTR_NM")
    private String distrNm;

    /**
     * 状态
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 等级编码
     */
    @TableField("LEVEL_ID")
    private Long levelId;

    /**
     * 联系电话
     */
    @TableField("PHONE")
    private String phone;

    private String payPwd;

    @TableField("SettledTm")
    private LocalDateTime settledTm;
}
