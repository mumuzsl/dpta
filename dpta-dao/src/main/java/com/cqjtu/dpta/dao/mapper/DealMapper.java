package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.dao.entity.DealD;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/14
 */
public interface DealMapper extends BaseMapper<Deal> {
    /**
     * 根据供应商和分销商id查询交易明细
     *
     * @param page
     * @param suppId
     * @param distrId
     * @return
     */
    IPage<Deal> selectBySuppAndDistr(Page<?> page, @Param("supp_id") Long suppId, @Param("distr_id") Long distrId);

    /**
     * 根据订单编码获取订单明细
     * @param deal_id
     * @return
     */
    List<DealD> getDealDByDealId(@Param("deal_id") Long deal_id);
}
