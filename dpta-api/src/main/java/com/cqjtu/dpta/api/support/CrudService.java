package com.cqjtu.dpta.api.support;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqjtu.dpta.common.extension.SearchPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Map;


/**
 * author: mumu
 * date: 2021/3/20
 */
public interface CrudService<T> extends IService<T> {

    default IPage<T> page(Pageable pageable) {
        return page(pageable, Wrappers.emptyWrapper());
    }

    default IPage<T> page(Pageable pageable, Wrapper<T> queryWrapper) {
        return page(convert(pageable), queryWrapper);
    }

    default IPage<Map<String, Object>> pageMaps(Pageable pageable) {
        return pageMaps(pageable, Wrappers.emptyWrapper());
    }

    default IPage<Map<String, Object>> pageMaps(Pageable pageable, Wrapper<T> queryWrapper) {
        return pageMaps(convert(pageable), queryWrapper);
    }

    default  <E> IPage<E> convert(Pageable pageable) {
        Page<E> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        return addSort(page, pageable.getSort());
    }

    default Page<T> toPage(Pageable pageable) {
        Page<T> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        return addSort(page, pageable.getSort());
    }

    static <T> IPage<T> toPage(org.springframework.data.domain.Page<T> page) {
        Page<T> page1 = new Page<>(page.getNumber() + 1, page.getSize(), page.getTotalElements());
        page1.setRecords(page.getContent());
        addSort(page1, page.getSort());
        return page1;
    }

    static <E extends Page<?>> E addSort(E page, Sort sort) {
        if (sort.isUnsorted()) {
            return page;
        }
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>(1);
        for (Sort.Order order : sort) {
            orderItems.add(new OrderItem(order.getProperty(), order.getDirection().isAscending()));
        }
        page.addOrder(orderItems);
        return page;
    }

    default SearchPage<T> toPage(Pageable pageable, String keyword) {
        SearchPage<T> searchPage = new SearchPage<>(pageable.getPageNumber(), pageable.getPageSize(), keyword);
        return addSort(searchPage, pageable.getSort());
    }
}
