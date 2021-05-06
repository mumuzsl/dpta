package com.cqjtu.dpta.api.support;

import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

public interface SettleService {
    /**
     * 日核销
     * @return
     */
    Boolean dayVerify();

    /**
     * 月结算
     * @return
     */
    Boolean MonthSettle();

    /**
     * 返回平台收益
     */
    Integer platSum(@Param("d") int d);
}
