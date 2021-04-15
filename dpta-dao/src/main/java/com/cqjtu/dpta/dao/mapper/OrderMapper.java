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
    IPage<Order> selectByShop(@Param("pg") SearchPage<?> page);

    IPage<Order> selectByComm(@Param("pg") SearchPage<?> page);

    IPage<Order> selectByState(@Param("pg") SearchPage<?> page);
    IPage<Order> selectByDistr(@Param("pg") SearchPage<?> page);


}
