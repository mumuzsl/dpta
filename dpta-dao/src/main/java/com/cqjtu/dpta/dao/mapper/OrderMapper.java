package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderDDto> getFullDetail(Long id);

    IPage<OrderDto> pageHalfOrderDto(Page<?> page, @Param("distrId") Long distrIdd);

    OrderDto getFullOrderDto(Long id);

    IPage<OrderDto> pageByDistrAndShop(@Param("pg") SearchPage<?> page, @Param("distrId") Long distrId);

    IPage<OrderDto> pageByDistrAndComm(@Param("pg") SearchPage<?> page, @Param("distrId") Long distrId);

    IPage<OrderDto> pageByDistrAndState(@Param("pg") SearchPage<?> page, @Param("distrId") Long distrId);

    IPage<Order> pageByDistr(Page<?> page, @Param("distrId") Long distrId);

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
    List<Order> getOrderListByDistrId(@Param("distr_id") Long distr_id);

}
