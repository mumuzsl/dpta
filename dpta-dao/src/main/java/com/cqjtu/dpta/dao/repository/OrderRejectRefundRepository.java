package com.cqjtu.dpta.dao.repository;

import java.util.Optional;

/**
 * author: mumu
 * date: 2021/5/28
 */
public interface OrderRejectRefundRepository {

    Optional<OrderRejectRefund> findByOrOrderId(Long orderId);

    void save(OrderRejectRefund orderRejectRefund);
}
