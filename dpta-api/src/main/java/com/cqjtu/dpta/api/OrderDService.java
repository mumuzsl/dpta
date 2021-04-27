package com.cqjtu.dpta.api;

import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.api.support.CrudService;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface OrderDService extends CrudService<OrderD> {
    /**
     * 判断该订单明细是否超过预留退款时间
     * @param comm_id 商品编码
     * @param time 商品购买时间
     * @return 为空时没有超过预留时间，反之超过
     */
    Long outRefundTime(Long comm_id, LocalDateTime time);

    /**
     * 根据商品编码获取该商品绑定的佣金规则
     * @param comm_id 商品编码
     * @return
     */
    CommR getCommR(Long comm_id);
}
