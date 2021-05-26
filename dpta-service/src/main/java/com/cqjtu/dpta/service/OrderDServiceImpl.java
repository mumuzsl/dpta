package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.mapper.OrderDMapper;
import com.cqjtu.dpta.api.OrderDService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class OrderDServiceImpl extends ServiceImpl<OrderDMapper, OrderD> implements OrderDService {

    /**
     * 判断该订单明细是否超过预留退款时间
     *
     * @param comm_id 商品编码
     * @param time    商品购买时间
     * @return 为空时没有超过预留时间，反之超过
     */
    @Override
    public Long outRefundTime(Long comm_id, LocalDateTime time) {
        return baseMapper.outRefundTime(comm_id, time);
    }

    /**
     * 根据商品编码获取该商品绑定的佣金规则
     *
     * @param comm_id 商品编码
     * @return
     */
    @Override
    public CommR getCommR(Long comm_id) {
        return baseMapper.getCommR(comm_id);
    }

    @Override
    public Long getOrderSum() {
        return baseMapper.getOrderSum();
    }

    public Integer clearBad(Long orderId) {
        return baseMapper.clearBad(orderId);
    }
}
