package com.cqjtu.dpta.web.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.cqjtu.dpta.api.support.CrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

/**
 * author: mumu
 * date: 2021/5/1
 */
public abstract class ControllerUtils {
    public static <E, T> List<E> quick(CrudService<E> crudService, String keyword, SFunction<E, T> etFunction) {
        return StringUtils.isBlank(keyword) ?
                crudService.list() :
                crudService.lambdaQuery().like(etFunction, keyword).list();
    }

    public static <E, T> IPage<E> quick(CrudService<E> crudService, Pageable pageable, String keyword, SFunction<E, T> etFunction) {
        return StringUtils.isBlank(keyword) ?
                crudService.page(crudService.toPage(pageable)) :
                crudService.lambdaQuery().like(etFunction, keyword).page(crudService.toPage(pageable));
    }

    public static <E, T extends IPage<E>> IPage<E> quick(String keyword, Supplier<T> notSearchSupplier, Supplier<T> searchSupplier) {
        return StringUtils.isBlank(keyword) ? notSearchSupplier.get() : searchSupplier.get();
    }

    public static BigDecimal money(String value) {
        return BigDecimal.valueOf(Double.parseDouble(value));
    }
}
