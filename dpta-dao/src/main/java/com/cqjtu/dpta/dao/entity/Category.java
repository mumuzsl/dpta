package com.cqjtu.dpta.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author ly
 * @date 2021-04-28 17:21
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_category")
public class Category {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    /**
     * 分类等级
     */
    @TableField("category_level")
    private Byte categoryLevel;


    /**
     * 父分类id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 父类名字
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 排序值
     */
    @TableField("category_rank")
    private Long categoryRank;

}
