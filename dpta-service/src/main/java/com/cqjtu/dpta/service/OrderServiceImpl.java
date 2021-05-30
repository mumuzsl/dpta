package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.OrderIndexService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.api.ShpCommService;
import com.cqjtu.dpta.api.SkuStockService;
import com.cqjtu.dpta.common.exception.BadRequestException;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.web.CommParam;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.dto.OrderStatisDto;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.ShpComm;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.cqjtu.dpta.dao.entity.emus.DeletedEnum;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.dao.mapper.OrderMapper;
import com.cqjtu.dpta.dao.repository.OrderRejectRefund;
import com.cqjtu.dpta.dao.repository.OrderRejectRefundRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderDService orderDService;
    @Resource
    private ShpCommService shpCommService;
    @Resource
    private SkuStockService skuStockService;
    @Resource
    private OrderIndexService orderIndexService;
    @Resource
    private OrderRejectRefundRepository orderRejectRefundRepository;

    @Override
    public void reject(OrderRejectRefund orderRejectRefund) {
        orderRejectRefund.setUpdateTime(LocalDateTime.now());

        orderRejectRefundRepository
                .findByOrOrderId(orderRejectRefund.getOrderId())
                .ifPresent(o -> orderRejectRefund.setId(o.getId()));

        orderRejectRefundRepository.save(orderRejectRefund);
    }

    @Override
    public boolean exists(Long id) {
        return query().eq("ID", id).count() > 0;
    }

    @Override
    public List<Order> getMonth(Long id, int year, int month) {
        String y = String.valueOf(year);
        String startMonth = DptaUtils.fillMonthStr(month);
        String endMonth = DptaUtils.fillMonthStr(month + 1);

        Map<String, Object> params = new HashMap<>(4);
        params.put("start", y + "-" + startMonth);
        params.put("end", y + "-" + endMonth);
        params.put("distrId", id);
        params.put("deleted", DeletedEnum.NOT_DELETE.value());

        return baseMapper.findByBetween(params);
    }

//    public IPage<OrderDto> searchByDistr(Long distrId, String keyword, Pageable pageable) {
//        Pageable newPageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
//        org.springframework.data.domain.Page<OrderIndex> orderIndices = null;
//        IPage<OrderDto> page = new Page<OrderDto>(pageable.getPageNumber(), pageable.getPageSize());
//        List<OrderDto> list = new ArrayList<>(pageable.getPageSize());
//        do {
//            list.clear();
//            orderIndices = orderDtoEsRepository.searchByDistr(distrId, keyword, newPageable);
//            orderIndices.forEach(orderIndex -> {
//                OrderDto orderDto = getOrderDto(orderIndex.getId());
//                if (orderDto == null) {
//                    orderDtoEsRepository.deleteById(orderIndex.getId());
//                } else {
//                    list.add(orderDto);
//                }
//            });
//
//            page.setTotal(orderIndices.getTotalElements());
//        } while (list.size() < orderIndices.getContent().size());
//
//        page.setRecords(list);
//        return page;
//    }

    @Override
    public OrderDto getOrderDto(Long orderId) {
        return getOrderDto(orderId, DeletedEnum.NOT_DELETE.value());
    }

    @Override
    public OrderDto getOrderDto(Long orderId, @Nullable Integer state) {
        OrderDto orderDto = baseMapper.getOrderDtoNotDetails(orderId, state);
        if (orderDto == null) {
            return orderDto;
        }
        orderDto.setDetails(getDetails(orderId));
        return orderDto;
    }

    @Override
    public List<OrderDDto> getDetails(Long orderId) {
        return baseMapper.getDetails(orderId);
    }

    @Override
    public IPage<OrderDto> pageOrderDtoByStates(Pageable pageable, Long distrId, List<Integer> state) {
        return pageOrderDtoByStatesAll(pageable, distrId, state, DeletedEnum.NOT_DELETE.value());
    }

    @Override
    public IPage<OrderDto> pageOrderDto(Pageable pageable, Long distrId, Integer state) {
        return pageOrderDtoAll(pageable, distrId, state, DeletedEnum.NOT_DELETE.value());
    }

    @Override
    public IPage<OrderDto> pageOrderDtoByStatesAll(Pageable pageable, Long distrId, List<Integer> state, Integer deleted) {
        IPage<OrderDto> page = baseMapper.pageOrderDtoNotDetailsByStates(toPage(pageable), distrId, state, deleted);
        page.getRecords().forEach(orderDto -> orderDto.setDetails(getDetails(orderDto.getId())));
        return page;
    }

    @Override
    public IPage<OrderDto> pageOrderDtoAll(Pageable pageable, Long distrId, Integer state, Integer deleted) {
        IPage<OrderDto> page = baseMapper.pageOrderDtoNotDetails(toPage(pageable), distrId, state, deleted);
        page.getRecords().forEach(orderDto -> orderDto.setDetails(getDetails(orderDto.getId())));
        return page;
    }

    private Order changeState(Long id, OrderState orderState) {
        if (log.isDebugEnabled()) {
            log.debug(id + "-" + orderState);
        }

        Order order = baseMapper.selectById(id);
        DptaUtils.notNull(order, "订单不存在: " + id);

        order.setState(orderState.state());
        return order;
    }


    @Override
    public int refundFinish(Long id) {
        return baseMapper.updateById(changeState(id, OrderState.REFUND_CLOSE));
    }

    @Override
    public int refund(Long id) {
        return baseMapper.updateById(changeState(id, OrderState.REFUNDING));
    }

    @Override
    public int success(Long id) {
        return baseMapper.updateById(changeState(id, OrderState.SUCCESS));
    }

    @Override
    public int sendComm(com.cqjtu.dpta.common.web.OrderParam orderParam) {
        Order order = changeState(orderParam.getId(), OrderState.STOCK_FINISH);
        order.setExpressNo(orderParam.getExpressNo());
        return baseMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int payOrder(Long id) {
        Order order = changeState(id, OrderState.PAYED);
        order.setPayTime(LocalDateTime.now());
        subStock(id);

        return baseMapper.updateById(order);
    }

    @Override
    public int refundMoney(Long id) {
        return 0;
    }

    @Override
    public int handClose(Long id) {
        return closeOrder(id, OrderState.HAND_CLOSE);
    }

    @Override
    public int overTimeClose(Long id) {
        return closeOrder(id, OrderState.OVRE_TIME_CLOSE);
    }

    @Override
    public int closeOrder(Long id, OrderState state) {
        Order order = baseMapper.selectById(id);
        DptaUtils.notNull(order, "订单不存在");

        OrderState orderState = OrderState.valueOf(order.getState());
        DptaUtils.notNull(orderState, "订单状态错误");

        switch (orderState) {
            case PAYED:
                refundMoney(id);
            case WAIT_PAY:
                freeStock(id);
                break;
            case STOCK_FINISH:
            default:
                if (log.isDebugEnabled()) {
                    log.debug("不符合订单取消条件-" + id + "-" + order.getState());
                }
                return 0;
        }

        order.setState(state.state());
        return baseMapper.updateById(order);
    }

    @Override
    public List<OrderStatisDto> countAndSum(Map<String, Object> map) {
        return baseMapper.countAndSum(map);
    }

    @Override
    public IPage<Order> pageByDistr(Pageable pageable, Long distrId, Integer state) {
        return baseMapper.pageByDistr(toPage(pageable), distrId, state);
    }

    @Override
    public Long getDistrId(Long id) {
        return baseMapper.getDistrId(id);
    }

    @Override
    public IPage<OrderDto> searchByDistrId(Pageable pageable,
                                           String keyword,
                                           Integer option,
                                           Long distrId,
                                           Integer state) {
        SearchPage<Order> page = toPage(pageable, keyword);
        switch (option) {
            case 0:
                return baseMapper.pageByDistrAndComm(page, distrId, state);
            case 1:
                return baseMapper.pageByDistrAndShop(page, distrId, state);
            default:
                return null;
        }
    }

    @Override
    public IPage<Order> search(Pageable pageable, String keyword, Integer option) {
        SearchPage<Order> page = toPage(pageable, keyword);
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
        return baseMapper.getOrderListByDistrId(distr_id, Const.VERIFY);
    }

    @Override
    @Transactional
    public Long create(com.cqjtu.dpta.common.web.OrderParam vo) {
        Order order = new Order();
        order.setShopId(vo.getShopId());
        order.setDatm(LocalDateTime.now());
        order.setState(OrderState.WAIT_PAY.state());
        order.setId(DptaUtils.defautlNextId());
        order.setAddress(vo.getAddress());
        order.setPhone(vo.getPhone());
        order.setReceiver(vo.getReceiver());

        save(order);

        BigDecimal amount = createDetail(vo, order.getId());
        order.setAmount(amount);

        updateById(order);

        return order.getId();
    }


    private BigDecimal createDetail(com.cqjtu.dpta.common.web.OrderParam vo, Long orderId) {
        BigDecimal amount = new BigDecimal(0);
        List<CommParam> comms = vo.getComms();
        for (CommParam curComm : comms) {
            ShpComm shpComm = getShpComm(curComm.getCommId(), vo.getShopId());

            SkuStock skuStock = skuStockService.getSkuStockByShopCommId(shpComm.getShopCommId());

            if (skuStock.getStock() <= skuStock.getLockedStock()
                    || skuStock.getStock() < curComm.getCount()) {
                orderDService.clearBad(orderId);
                throw new BadRequestException(ResultCodeEnum.STOCK_ERROR);
            }

            skuStockService.lock(skuStock, curComm.getCount());

            BigDecimal shopAmount = skuStock
                    .getPrice()
                    .multiply(BigDecimal.valueOf(curComm.getCount()));
            amount = amount.add(shopAmount);

            OrderD orderD = new OrderD();
            orderD.setOrderId(orderId);
            orderD.setCommId(curComm.getCommId());
            orderD.setPrice(skuStock.getPrice());
            orderD.setCount(curComm.getCount());
            orderD.setSkuId(skuStock.getSkuId());

            orderDService.save(orderD);
        }

        return amount;
    }

    public ShpComm getShpComm(Long commId, Long shopId) {
        return shpCommService
                .query()
                .eq("SHOP_ID", shopId)
                .eq("COMM_ID", commId)
                .oneOpt()
                .orElseThrow(() -> new BadRequestException(shopId + ":" + commId));
    }

    public void subStock(Long orderId) {
        forOrder(orderId, skuStockService::subStock);
    }

    private void freeStock(Long orderId) {
        forOrder(orderId, skuStockService::freeStock);
    }

    private void forOrder(Long orderId, BiConsumer<Long, Integer> consumer) {
        Long shopId = getById(orderId).getShopId();
        List<OrderD> ods = orderDService.query().eq("ORDER_ID", orderId).list();
        for (OrderD od : ods) {
            ShpComm shpComm = getShpComm(od.getCommId(), shopId);
            consumer.accept(shpComm.getShopCommId(), od.getCount());
        }
    }
}
