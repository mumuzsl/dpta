package com.cqjtu.dpta.api.support;

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
}
