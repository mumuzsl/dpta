package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.dao.mapper.ShopMapper;
import com.cqjtu.dpta.api.ShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
