package com.cqjtu.dpta.api.support;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

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

    default <E> IPage<E> convert(Pageable pageable, Class<E> e) {
        return new Page<>(pageable.getPageNumber(), pageable.getPageSize());
    }

    default <E> IPage<E> convert(Pageable pageable) {
        return new Page<E>(pageable.getPageNumber(), pageable.getPageSize());
    }

    default Page<?> toPage(Pageable pageable) {
        return new Page<>(pageable.getPageNumber(), pageable.getPageSize());
    }
}
