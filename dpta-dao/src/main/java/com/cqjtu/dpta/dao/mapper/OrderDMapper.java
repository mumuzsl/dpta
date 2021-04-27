package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface OrderDMapper extends BaseMapper<OrderD> {

    /**
     * 判断该订单明细是否超过预留退款时间
     * @param comm_id 商品编码
     * @param time 商品购买时间
     * @return 为空时没有超过预留时间，反之超过
     */
    Long outRefundTime(@Param("comm_id") Long comm_id, @Param("time") LocalDateTime time);

    /**
     * 根据商品编码获取该商品绑定的佣金规则
     *
     * @param comm_id 商品编码
     * @return
     */
    CommR getCommR(@Param("comm_id") Long comm_id);
}
