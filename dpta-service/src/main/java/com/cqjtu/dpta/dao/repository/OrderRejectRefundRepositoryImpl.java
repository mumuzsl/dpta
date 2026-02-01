package com.cqjtu.dpta.dao.repository;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: mumu
 * @date: 2026/1/24 21:43
 */
@Service
public class OrderRejectRefundRepositoryImpl implements OrderRejectRefundRepository {
    @Override
    public Optional<OrderRejectRefund> findByOrOrderId(Long orderId) {
        return Optional.empty();
    }

    @Override
    public void save(OrderRejectRefund orderRejectRefund) {

    }
}
