package com.cqjtu.dpta.api;

import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
public interface SkuStockService extends CrudService<SkuStock> {
    /**
     *
     * @param shopCommId
     * @param jsonStr
     * @param stock
     */
    void create(Long shopCommId, String jsonStr, Integer stock);

    /**
     * 锁定库存，注意：没有更新skuStock对象中的数据
     * @param skuStock
     * @param num
     * @return
     */
    void lock(SkuStock skuStock, int num);

    void subtractStock(List<OrderD> ods);

    void freeStock(List<OrderD> ods);
}
