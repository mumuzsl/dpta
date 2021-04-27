package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.PafSkuStock;
import com.cqjtu.dpta.dao.mapper.PafCommMapper;
import com.cqjtu.dpta.api.PafCommService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台-商品表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class PafCommServiceImpl extends ServiceImpl<PafCommMapper, PafComm> implements PafCommService {

    /**
     * @param comm_id 商品编码
     * @param sku_id
     * @return
     */
    @Override
    public PafSkuStock getByCommIdAndSkuId(Long comm_id, Long sku_id) {
        return baseMapper.getByCommIdAndSkuId(comm_id,sku_id);
    }
}
