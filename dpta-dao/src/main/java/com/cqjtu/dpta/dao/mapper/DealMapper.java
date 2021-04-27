package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.dao.entity.Deal;
import org.apache.ibatis.annotations.Param;

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
}
