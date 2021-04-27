package com.cqjtu.dpta.api;

import com.cqjtu.dpta.dao.entity.ResveD;
import com.cqjtu.dpta.api.support.CrudService;

/**
 * <p>
 * 预备金明细表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface ResveDService extends CrudService<ResveD> {
    /**
     * 获取插入时的ID
     * @return
     */
    public Long getInsertId();
}
