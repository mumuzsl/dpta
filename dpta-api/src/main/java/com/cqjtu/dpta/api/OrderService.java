package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.vo.OrderVo;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.OrderD;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */

public interface OrderService extends CrudService<Order> {

    IPage<Order> search(Pageable pageable, String keyword, Integer option);

    void create(OrderVo vo);

    Long getDistrId(Long id);

//    IPage<Order> pageByDistrId(Long distrId);
}
