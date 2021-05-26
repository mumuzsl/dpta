package com.cqjtu.dpta.api;

import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.OrderD;

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
     *
     * @param comm_id 商品编码
     * @param time    商品购买时间
     * @return 为空时没有超过预留时间，反之超过
     */
    Long outRefundTime(Long comm_id, LocalDateTime time);

    /**
     * 根据商品编码获取该商品绑定的佣金规则
     *
     * @param comm_id 商品编码
     * @return
     */
    CommR getCommR(Long comm_id);

    /**
     * 返回订单数
     */
    Long getOrderSum();

    /**
     * 清除“坏掉”的订单详情。
     * 比如在创建订单时发生异常，导致创建了订单详情，
     * 却没有创建订单，这些已被创建的订单详情需要清理。
     */
    Integer clearBad(Long orderId);
}
