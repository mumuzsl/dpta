package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.mapper.OrderMapper;
import com.cqjtu.dpta.api.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public IPage<Order> search(Pageable pageable, String keyword, Integer option) {
        SearchPage<Order> page = SearchPage.toPage(pageable, keyword);
        switch (option) {
            case 0:
                return baseMapper.selectByComm(page);
            case 1:
                return baseMapper.selectByShop(page);
            case 2:
                return baseMapper.selectByState(page);
            case 3:
                return baseMapper.selectByDistr(page);
            default:
                return null;
        }
    }
}
