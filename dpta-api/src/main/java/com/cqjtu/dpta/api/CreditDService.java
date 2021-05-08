package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.CreditD;
import org.springframework.data.domain.Pageable;

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

    IPage<CreditD>  pageByDistrAndType(Pageable pageable,Long distrId,Integer type);
}
