package com.cqjtu.dpta.api.support;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
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


    PageResult getAll(PageQueryUtil pageUtil);

    PageResult getByMonth(PageQueryUtil pageUtil, String month);
}
