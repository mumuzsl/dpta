package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.dao.entity.TaxR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 税费规则表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface TaxRMapper extends BaseMapper<TaxR> {

    /**
     * 根据个人所得获取对应的税费规则
     * @param amount
     * @return
     */
    TaxR getTaxR (@Param("amount") BigDecimal amount);

    List<TaxR> getList(PageQueryUtil pageUtil);
}
