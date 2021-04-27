package com.cqjtu.dpta.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 交易明细表
 * </p>
 *
 * @author mumu
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_deal")
public class Deal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编码
     */
    @TableId(value = "DEAL_ID", type = IdType.AUTO)
    private Long dealId;

    /**
     * 总金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 分销商编码
     */
    @TableField("DISTR_ID")
    private Long distrId;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
