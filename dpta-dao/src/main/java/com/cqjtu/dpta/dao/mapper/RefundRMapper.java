package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 退款规则表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface RefundRMapper extends BaseMapper<RefundR> {
    IPage<RefundR> bindSort(Page<?> page);
}
