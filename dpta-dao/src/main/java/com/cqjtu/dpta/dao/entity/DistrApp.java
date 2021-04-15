package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分销应用表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_distr_app")
public class DistrApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编码
     */
    @TableId("APP_ID")
    private Long appId;

    /**
     * 应用名称
     */
    @TableField("APP_NM")
    private String appNm;

    /**
     * 应用类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 跳转地址
     */
    @TableField("JUMP_ADDR")
    private String jumpAddr;

    /**
     * 平台识别码
     */
    @TableField("IDENT_CD")
    private String identCd;

    /**
     * 状态
     */
    @TableField("STATE")
    private Integer state;


}
