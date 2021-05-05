package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 授信明细表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CreditDMapper extends BaseMapper<CreditD> {

    List<CreditD> getRecodrs(PageQueryUtil pageUtil, @Param("id") Long id);
}
