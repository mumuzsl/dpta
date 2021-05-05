package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.api.support.CrudService;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 商铺表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface ShopService extends CrudService<Shop> {

    public int countShop();

    IPage<PafComm> getInsellComms(Pageable pageable, long distrId, Long shopId);
}
