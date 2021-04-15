package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.mapper.OrderDMapper;
import com.cqjtu.dpta.api.OrderDService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
