package com.cqjtu.dpta.service;

import com.cqjtu.dpta.api.ShopService;
import com.cqjtu.dpta.api.VisitsService;
import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.dao.repository.Visits;
import com.cqjtu.dpta.dao.repository.VisitsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/26
 */
@Service
public class VisitsServiceImpl implements VisitsService {

    @Resource
    private VisitsRepository visitsRepository;
    @Resource
    private ShopService shopService;

    @Override
    public Visits add(Long shopId) {
        return add(shopId, null);
    }

    @Override
    public Visits add(Long shopId, String userId) {
        Shop shop = shopService
                .lambdaQuery()
                .eq(Shop::getShopId, shopId)
                .one();

        return shop == null ? null : add(shop.getDistrId(), shopId, userId);
    }

    @Override
    public Visits add(Long distrId, Long shopId, String userId) {
        Assert.notNull(shopId, "商铺id");

        Visits visits = new Visits();
        visits.setShopId(shopId);
        visits.setDistrId(distrId);
        visits.setDate(LocalDateTime.now());
        visits.setUserId(userId);

        return visitsRepository.save(visits);
    }

}
