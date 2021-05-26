package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.dao.repository.OrderIndex;
import org.springframework.data.domain.Pageable;

import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/5/18
 */
public interface OrderIndexService {
    IPage<OrderDto> searchAllOrder(String keyword, Pageable pageable);

    IPage<OrderDto> searchByDistr(Long distrId, String keyword, Pageable pageable);

    OrderIndex LogicDelete(Long id);

    OrderIndex updateState(Long id, OrderState state);

    OrderIndex update(Long id, Consumer<OrderIndex> consumer);

    void create(OrderDto orderDto);
}
