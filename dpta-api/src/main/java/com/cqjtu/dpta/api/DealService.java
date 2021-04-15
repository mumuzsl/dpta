package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.Deal;
import org.springframework.data.domain.Pageable;

/**
 * author: mumu
 * date: 2021/4/14
 */
public interface DealService extends CrudService<Deal> {
    IPage<Deal> pageBySuppAndDistr(Pageable pageable, Long suppId, Long distrId);
}
