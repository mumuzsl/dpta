package com.cqjtu.dpta.api;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.api.support.CrudService;

/**
 * <p>
 * 授信明细表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CreditDService extends CrudService<CreditD> {

    /**
     * 向数据库插入一条授信明细
     * @param creditD
     * @return
     */
    Boolean addCreditD(CreditD creditD);

    PageResult getRecords(PageQueryUtil pageUtil, Long id);
}
