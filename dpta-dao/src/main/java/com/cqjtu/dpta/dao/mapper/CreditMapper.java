package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.entity.Credit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqjtu.dpta.dao.entity.Deal;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 授信表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CreditMapper extends BaseMapper<Credit> {
    IPage<Credit> applyBySuppNm(SearchPage<?> page, @Param("state") Integer state);

    IPage<Credit> applyByDistrNm(SearchPage<?> page, @Param("state") Integer state);


}
