package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.SkuService;
import com.cqjtu.dpta.api.SkuStockService;
import com.cqjtu.dpta.common.exception.BadRequestException;
import com.cqjtu.dpta.dao.entity.Sku;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.cqjtu.dpta.dao.mapper.SkuStockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
@Service
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements SkuStockService {
    @Resource
    private SkuService skuService;

    @Override
    public SkuStock getSkuStockByShopCommId(Long shopCommId) {
        return query()
                .eq("shop_comm_id", shopCommId)
                .one();
    }

    public void create(Long shopCommId, String jsonStr, Integer stock) {
        if (getById(shopCommId) == null) {
            throw new BadRequestException("commodify id error");
        }

        Sku sku = new Sku();
        sku.setSpec(jsonStr);
        skuService.save(sku);

        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(sku.getId());
        skuStock.setShopCommId(shopCommId);
        skuStock.setStock(stock);
        save(skuStock);
    }

    @Override
    public void lock(Long skuCommId, int num) {
        lock(getSkuStockByShopCommId(skuCommId), num);
//        baseMapper.lockStock(skuCommId, num);
    }

    @Override
    public void subStock(Long skuCommId, int num) {
        subStock(getSkuStockByShopCommId(skuCommId), num);
//        baseMapper.subtractStock(skuCommId, num);
    }

    @Override
    public void freeStock(Long shpCommId, int num) {
        freeStock(getSkuStockByShopCommId(shpCommId), num);
//        baseMapper.freeStock(shpCommId, num);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void lock(SkuStock skuStock, int num) {
        skuStock.setLockedStock(skuStock.getLockedStock() + num);
        baseMapper.updateById(skuStock);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void subStock(SkuStock skuStock, int num) {
        skuStock.setLockedStock(skuStock.getLockedStock() - num);
        skuStock.setStock(skuStock.getStock() - num);
        baseMapper.updateById(skuStock);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void freeStock(SkuStock skuStock, int num) {
        skuStock.setLockedStock(skuStock.getLockedStock() - num);
        baseMapper.updateById(skuStock);
    }
}
