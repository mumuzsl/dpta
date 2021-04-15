package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 角色
     */
    @TableField("ROLE")
    private Integer role;

    /**
     * 对应表ID
     */
    @TableField("OTHER_ID")
    private Long otherId;

    @TableField("PASSWD")
    private String passwd;


}
