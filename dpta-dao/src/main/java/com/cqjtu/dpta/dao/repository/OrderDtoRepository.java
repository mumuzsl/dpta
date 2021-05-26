package com.cqjtu.dpta.dao.repository;

import com.cqjtu.dpta.dao.dto.OrderDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * author: mumu
 * date: 2021/5/20
 */
@Repository
public interface OrderDtoRepository extends MongoRepository<OrderDto, Long> {
}
