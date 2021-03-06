package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.dto.OrderStatisDto;
import com.cqjtu.dpta.dao.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> findByBetween(@Param("params") Map<String, Object> map);

    List<OrderStatisDto> countAndSum(@Param("params") Map<String, Object> map);

    List<OrderDDto> getDetails(Long orderId);

    IPage<OrderDto> pageOrderDtoNotDetails(Page<?> page,
                                           @Param("distrId") Long distrId,
                                           @Param("state") Integer state,
                                           @Param("deleted") Integer deleted);

    IPage<OrderDto> pageOrderDtoNotDetailsByStates(Page<?> page,
                                                   @Param("distrId") Long distrId,
                                                   @Param("state") List<Integer> state,
                                                   @Param("deleted") Integer deleted);

    OrderDto getOrderDtoNotDetails(@Param("id") Long orderId, @Param("deleted") Integer deleted);

    IPage<OrderDto> pageByDistrAndShop(@Param("pg") SearchPage<?> page,
                                       @Param("distrId") Long distrId,
                                       @Param("state") Integer state);

    IPage<OrderDto> pageByDistrAndComm(@Param("pg") SearchPage<?> page,
                                       @Param("distrId") Long distrId,
                                       @Param("state") Integer state);

    IPage<OrderDto> pageByDistrAndState(@Param("pg") SearchPage<?> page, @Param("distrId") Long distrId);

    IPage<Order> pageByDistr(Page<?> page,
                             @Param("distrId") Long distrId,
                             @Param("state") Integer state);

    /**
     * 获取分销商id
     */
    Long getDistrId(Long id);

    /**
     * 根据商铺名称搜索
     *
     * @param page
     * @return
     */
    IPage<Order> selectByShop(@Param("pg") SearchPage<?> page);


    /**
     * 根据商品名称搜索
     *
     * @param page
     * @return
     */
    IPage<Order> selectByComm(@Param("pg") SearchPage<?> page);

    /**
     * 根据状态搜索
     *
     * @param page
     * @return
     */
    IPage<Order> selectByState(@Param("pg") SearchPage<?> page);


    /**
     * 根据分销商名称搜索
     *
     * @param page
     * @return
     */
    IPage<Order> selectByDistr(@Param("pg") SearchPage<?> page);

    /**
     * 根据分销商编码获取分销商名下店铺的所有已核销订单
     *
     * @param distr_id 分销商编码
     * @return
     */
    List<Order> getOrderListByDistrId(@Param("distr_id") Long distr_id, @Param("state") Integer state);

}
