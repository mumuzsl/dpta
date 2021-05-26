package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.*;
import com.cqjtu.dpta.dao.mapper.PafCommMapper;
import com.cqjtu.dpta.api.PafCommService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<ShopTop> getShopTop() {
        return baseMapper.getShopTop();
    }

    @Override
    public List<DistrTop> getDistrTop() {
        return baseMapper.getDistrTop();
    }

    @Override
    public String getMaxShop(String d) {
        return baseMapper.getMaxShop(d);
    }

    @Override
    public List<DistrSumM> getDistrSumM(String d) {
        return baseMapper.getDistrSumM(d);
    }

    @Override
    public List<DistrSumM> getDistrSumN(String d) {
        return baseMapper.getDistrSumN(d);
    }

    @Override
    public BigDecimal getDistrSum(String d, int b) {
        return baseMapper.getDistrSum(d,b);
    }

    @Override
    public List<Date> getAllDate() {
        return baseMapper.getAllDate();
    }

    @Override
    public List<ShopPredict> getSPredict() {
        return baseMapper.getSPredict();
    }

}
