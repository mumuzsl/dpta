package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.dao.entity.CommR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 佣金规则表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CommRMapper extends BaseMapper<CommR> {
    IPage<CommR> bindSort(Page<?> page);
}
