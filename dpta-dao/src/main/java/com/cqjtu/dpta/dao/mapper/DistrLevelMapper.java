package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    Integer getMonths(@Param("distrId") Long distrID);

    BigDecimal getSumPafP(@Param("distrId") Long distrID);

}
