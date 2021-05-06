package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.dao.mapper.ShopMapper;
import com.cqjtu.dpta.api.ShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商铺表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Override
    public int countShop() {
        return baseMapper.countShop();
    }

    @Override
    public IPage<PafComm> getInsellComms(Pageable pageable, long distrId, Long shopId) {
        return baseMapper.getComms(toPage(pageable), distrId, shopId, Const.INSELL);
    }
}
