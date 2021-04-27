package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.extension.SearchPage;
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


}
