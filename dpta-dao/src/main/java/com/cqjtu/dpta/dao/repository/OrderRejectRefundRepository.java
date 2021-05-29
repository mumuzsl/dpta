package com.cqjtu.dpta.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * author: mumu
 * date: 2021/5/28
 */
public interface OrderRejectRefundRepository extends MongoRepository<OrderRejectRefund, String> {

    Optional<OrderRejectRefund> findByOrOrderId(Long orderId);

}
