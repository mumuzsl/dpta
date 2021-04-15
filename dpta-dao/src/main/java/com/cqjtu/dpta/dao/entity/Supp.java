package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_supp")
public class Supp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商编码
     */
    @TableId("SUPP_ID")
    private Long suppId;

    /**
     * 供应商名称
     */
    @TableField("SUPP_NM")
    private String suppNm;

    /**
     * 联系电话
     */
    @TableField("PHONE")
    private String phone;


}
