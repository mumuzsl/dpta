package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.api.OrderIndexService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.dao.dto.OrderDDto;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.entity.emus.DeletedEnum;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.dao.repository.CommIndex;
import com.cqjtu.dpta.dao.repository.OrderIndexRepository;
import com.cqjtu.dpta.dao.repository.OrderIndex;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/5/18
 */
@Service
public class OrderIndexServiceImpl implements OrderIndexService {

    @Resource
    private OrderIndexRepository orderIndexRepository;
    @Resource
    private OrderService orderService;

    private IPage<OrderDto> convert(org.springframework.data.domain.Page<OrderIndex> page) {
        return new Page<OrderDto>(page.getNumber(), page.getSize());
    }

    private IPage<OrderDto> full(org.springframework.data.domain.Page<OrderIndex> page) {
        IPage<OrderDto> ipage = convert(page);
        ArrayList<OrderDto> list = new ArrayList<>(page.getSize());
        page.forEach(orderIndex -> list.add(orderService.getOrderDto(orderIndex.getId())));
        ipage.setRecords(list);
        ipage.setTotal(page.getTotalElements());
        return ipage;
    }

    @Override
    public IPage<OrderDto> searchAllOrder(String keyword, Pageable pageable) {
        org.springframework.data.domain.Page<OrderIndex> orderIndexPage =
                orderIndexRepository.searchAllOrder(keyword, amend(pageable));
        return full(orderIndexPage);
    }

    private Pageable amend(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    }

    @Override
    public IPage<OrderDto> searchByDistr(Long distrId, String keyword, Pageable pageable) {
        org.springframework.data.domain.Page<OrderIndex> orderIndexPage =
                orderIndexRepository.searchByDistr(distrId, keyword, DeletedEnum.NOT_DELETE.value(), amend(pageable));
        return full(orderIndexPage);
    }

    @Override
    public OrderIndex LogicDelete(Long id) {
        return update(id, orderIndex -> orderIndex.setDeleted(DeletedEnum.DELETED.value()));
    }

    @Override
    public OrderIndex updateState(Long id, OrderState state) {
        return update(id, orderIndex -> orderIndex.setState(state.state()));
    }

    @Override
    public OrderIndex update(Long id, Consumer<OrderIndex> consumer) {
        return orderIndexRepository
                .findById(id)
                .map(orderIndex -> {
                    consumer.accept(orderIndex);
                    orderIndexRepository.save(orderIndex);
                    return orderIndex;
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void create(OrderDto orderDto) {
        OrderIndex orderIndex = new OrderIndex();
        BeanUtils.copyProperties(orderDto, orderIndex, "details");
        List<OrderDDto> details = orderDto.getDetails();
        ArrayList<CommIndex> newDetails = new ArrayList<>(details.size());
        details.forEach(orderDDto -> {
            CommIndex commIndex = new CommIndex();
            commIndex.setId(orderDDto.getCommId());
            commIndex.setName(orderDDto.getGoodsName());
            commIndex.setCount(orderDDto.getCount());
            commIndex.setPrice(orderDDto.getPrice());

            newDetails.add(commIndex);
        });

        orderIndex.setDetails(newDetails);
        orderIndexRepository.save(orderIndex);
    }

}
