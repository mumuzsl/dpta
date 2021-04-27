package com.cqjtu.dpta.service;

import com.cqjtu.dpta.api.SkuService;
import com.cqjtu.dpta.common.exception.BadRequestException;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.Sku;
import com.cqjtu.dpta.dao.entity.SkuStock;
import com.cqjtu.dpta.dao.mapper.SkuStockMapper;
import com.cqjtu.dpta.api.SkuStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    public void lock(SkuStock skuStock, int num) {
        if (skuStock.getStock() <= skuStock.getLockedStock()
                || skuStock.getStock() < num) {
            throw new BadRequestException(ResultCodeEnum.STOCK_ERROR);
        }
        baseMapper.lockStock(skuStock.getId(), num);
    }

    @Override
    public void subtractStock(List<OrderD> ods) {
        for (OrderD od : ods) {
            baseMapper.subtractStock(od.getCommId(), od.getCount(), od.getCount());
        }
    }

    @Override
    public void freeStock(List<OrderD> ods) {
        for (OrderD od : ods) {
            baseMapper.freeStock(od.getCommId(), od.getCount(), od.getCount());
        }
    }
}
