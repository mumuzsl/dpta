package com.cqjtu.dpta.service;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.ShpCommService;
import com.cqjtu.dpta.api.SkuStockService;
import com.cqjtu.dpta.common.exception.BadRequestException;
import com.cqjtu.dpta.common.exception.OrderException;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.vo.CommVo;
import com.cqjtu.dpta.common.vo.OrderVo;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.ShpComm;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.cqjtu.dpta.dao.mapper.OrderMapper;
import com.cqjtu.dpta.api.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Resource
    private OrderDService orderDService;

    @Resource
    private ShpCommService shpCommService;

    @Resource
    private SkuStockService skuStockService;

    @Override
    public Long getDistrId(Long id) {
        return baseMapper.getDistrId(id);
    }

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

    @Override
    @Transactional
    public void create(OrderVo vo) {
        Order order = new Order();
        order.setShopId(vo.getShopId());
        order.setDatm(LocalDateTime.now());
        save(order);

        BigDecimal amount = new BigDecimal(0);
        List<CommVo> comms = vo.getComms();
        for (CommVo curComm : comms) {
            SkuStock skuStock = skuStockService.getById(curComm.getSkuStockId());

            skuStockService.lock(skuStock, curComm.getCount());

            BigDecimal shopAmount = skuStock
                    .getPrice()
                    .multiply(BigDecimal.valueOf(curComm.getCount()));
            amount = amount.add(shopAmount);

            OrderD orderD = new OrderD();
            orderD.setOrderId(order.getId());
            orderD.setCommId(skuStock.getPafCommId());
            orderD.setPrice(skuStock.getPrice());
            orderD.setCount(curComm.getCount());
            orderD.setSkuId(skuStock.getSkuId());

            orderDService.save(orderD);
        }

        order.setAmount(amount);
        updateById(order);
    }

    public void subStock(Long orderId) {
        List<OrderD> ods = getDetail(orderId);
        skuStockService.subtractStock(ods);
    }

    public void freeStock(Long orderId) {
        List<OrderD> ods = getDetail(orderId);
        skuStockService.freeStock(ods);
    }

    private List<OrderD> getDetail(Long orderId) {
        QueryWrapper<OrderD> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", orderId);
        return orderDService.list(queryWrapper);
    }
}
