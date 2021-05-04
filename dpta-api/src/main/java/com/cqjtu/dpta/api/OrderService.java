package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.vo.OrderParam;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.api.support.CrudService;
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
    List<OrderDDto> getFullDetail(Long id);

    IPage<OrderDto> pageNotDetailOrderDto(Pageable pageable, Long distrId);

    OrderDto getFullOrderDto(Long orderId);

    IPage<Order> search(Pageable pageable, String keyword, Integer option);

    IPage<OrderDto> searchByDistrId(Pageable pageable, String keyword, Integer option, Long distrId);

    /**
     * 根据分销商编码获取分销商名下店铺的所有已核销订单
     *
     * @param distr_id 分销商编码
     * @return
     */
    List<Order> getOrderListByDistrId(Long distr_id);

    void create(OrderParam vo);

    Long getDistrId(Long id);

    IPage<Order> pageByDistr(Pageable pageable, Long distrId);

//    IPage<Order> pageByDistrId(Long distrId);
}
