package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.common.web.OrderParam;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.dto.OrderStatisDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */

public interface OrderService extends CrudService<Order> {
    boolean exists(Long id);

    List<Order> getMonth(Long id, int year, int month);

    List<OrderStatisDto> countAndSum(Map<String, Object> map);

    IPage<Order> search(Pageable pageable, String keyword, Integer option);

    IPage<OrderDto> searchByDistrId(Pageable pageable,
                                    String keyword,
                                    Integer option,
                                    Long distrId,
                                    Integer state);

    /**
     * 根据分销商编码获取分销商名下店铺的所有已核销订单
     *
     * @param distr_id 分销商编码
     * @return
     */
    List<Order> getOrderListByDistrId(Long distr_id);

    Long create(OrderParam vo);

    Long getDistrId(Long id);

    IPage<Order> pageByDistr(Pageable pageable, Long distrId, Integer state);

    int refundMoney(Long id);

    int handClose(Long id);

    int overTimeClose(Long id);

    int closeOrder(Long id, OrderState orderState);

    int payOrder(Long id);

    int sendComm(OrderParam id);

    int success(Long id);

    int refund(Long id);

    IPage<OrderDto> pageOrderDtoByStates(Pageable pageable, Long distrId, List<Integer> state);

    IPage<OrderDto> pageOrderDto(Pageable pageable, Long distrId, Integer state);

    IPage<OrderDto> pageOrderDtoByStatesAll(Pageable pageable, Long distrId, List<Integer> state, Integer deleted);

    IPage<OrderDto> pageOrderDtoAll(Pageable pageable, Long distrId, Integer state, Integer deleted);

    int refundFinish(Long id);

    OrderDto getOrderDto(Long orderId, @Nullable Integer state);

    List<OrderDDto> getDetails(Long orderId);

    OrderDto getOrderDto(Long orderId);

    void subStock(Long orderId);
}
