package com.cqjtu.dpta.api;

import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.PafSkuStock;

/**
 * <p>
 * 平台-商品表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface PafCommService extends CrudService<PafComm> {
    /**
     *
     * @param comm_id 商品编码
     * @param sku_id
     * @return
     */
    PafSkuStock getByCommIdAndSkuId(Long comm_id, Long sku_id);
}
