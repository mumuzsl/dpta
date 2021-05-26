package com.cqjtu.dpta.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SettleMVo {
    private Long id;

    /**
     * 分销商编码
     */
    private String distrNm;

    /**
     * 佣金
     */
    private BigDecimal comP;

    /**
     * 税金
     */
    private BigDecimal taxP;

    /**
     * 平台分润（未扣税）
     */
    private BigDecimal plaP;

    /**
     * 本金
     */
    private BigDecimal basP;

    /**
     * 结算日期
     */
    private LocalDateTime settleTm;
}
