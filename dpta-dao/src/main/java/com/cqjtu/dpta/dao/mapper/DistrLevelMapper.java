package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 分销商等级表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface DistrLevelMapper extends BaseMapper<DistrLevel> {

    List<DistrLevel> getList(PageQueryUtil queryUtil);

}
