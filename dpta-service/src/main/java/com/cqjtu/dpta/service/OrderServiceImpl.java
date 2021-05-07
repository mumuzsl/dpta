package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.ShpCommService;
import com.cqjtu.dpta.api.SkuStockService;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.common.vo.CommVo;
import com.cqjtu.dpta.common.web.OrderParam;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.cqjtu.dpta.dao.mapper.OrderMapper;
import com.cqjtu.dpta.api.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    public List<OrderDDto> getFullDetail(Long id) {
        return baseMapper.getFullDetail(id);
    }

    @Override
    public IPage<OrderDto> pageNotDetailOrderDto(Pageable pageable, Long distrId) {
        return baseMapper.pageHalfOrderDto(toPage(pageable), distrId);
    }

    @Override
    public OrderDto getFullOrderDto(Long orderId) {
        return baseMapper.getFullOrderDto(orderId);
    }

    @Override
    public IPage<Order> pageByDistr(Pageable pageable, Long distrId) {
        return baseMapper.pageByDistr(toPage(pageable), distrId);
    }

    @Override
    public Long getDistrId(Long id) {
        return baseMapper.getDistrId(id);
    }

    @Override
    public IPage<OrderDto> searchByDistrId(Pageable pageable, String keyword, Integer option, Long distrId) {
        SearchPage<Order> page = SearchPage.toPage(pageable, keyword);
        switch (option) {
            case 0:
                return baseMapper.pageByDistrAndComm(page, distrId);
            case 1:
                return baseMapper.pageByDistrAndShop(page, distrId);
            case 2:
                return baseMapper.pageByDistrAndState(page, distrId);
            default:
                return null;
        }
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

    /**
     * 根据分销商编码获取分销商名下店铺的所有已核销订单
     *
     * @param distr_id 分销商编码
     * @return
     */
    @Override
    public List<Order> getOrderListByDistrId(Long distr_id) {
        return baseMapper.getOrderListByDistrId(distr_id);
    }

    @Override
    @Transactional
    public void create(OrderParam vo) {
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
