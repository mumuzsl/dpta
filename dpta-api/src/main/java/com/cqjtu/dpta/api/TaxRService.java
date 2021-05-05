package com.cqjtu.dpta.api;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.TaxR;
import com.cqjtu.dpta.api.support.CrudService;

import java.math.BigDecimal;

/**
 * <p>
 * 税费规则表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface TaxRService extends CrudService<TaxR> {
    /**
     * 根据个人所得获取对应的税费规则
     * @param amount
     * @return
     */
    TaxR getTaxR (BigDecimal amount);

    PageResult getList(PageQueryUtil pageUtil);
}
