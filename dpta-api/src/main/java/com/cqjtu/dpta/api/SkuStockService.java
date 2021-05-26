package com.cqjtu.dpta.api;

import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.SkuStock;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
public interface SkuStockService extends CrudService<SkuStock> {
    SkuStock getSkuStockByShopCommId(Long shopCommId);

    /**
     * @param shopCommId
     * @param jsonStr
     * @param stock
     */
    void create(Long shopCommId, String jsonStr, Integer stock);

    /**
     * 锁定库存，注意：没有更新skuStock对象中的数据
     */
    void lock(Long skuCommId, int num);

    void subStock(Long skuCommId, int num);

    void freeStock(Long shpCommId, int num);

    void lock(SkuStock skuStock, int num);

    void subStock(SkuStock skuStock, int num);

    void freeStock(SkuStock skuStock, int num);
}
