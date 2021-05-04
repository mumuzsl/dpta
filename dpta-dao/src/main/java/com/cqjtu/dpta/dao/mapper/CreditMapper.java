package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.entity.Credit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqjtu.dpta.dao.entity.Distr;
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


    IPage<Credit> pageByDistrAndState(@Param("page") SearchPage<?> page,
                                      @Param("distrId") Long distrId,
                                      @Param("state") Integer state);

    IPage<Distr> pageBySuppAndState(@Param("page") SearchPage<?> page,
                                    @Param("suppId") Long suppId,
                                    @Param("state") Integer state);

    /**
     * 根据供应商名称和授信状态搜索授信
     *
     * @param page  分页类，包含关键字
     * @param state
     * @return
     */
    IPage<Credit> applyBySuppNm(@Param("page") SearchPage<?> page, @Param("state") Integer state);

    /**
     * 根据分销商名称和授信状态搜索授信
     *
     * @param page  分页类，包含关键字
     * @param state
     * @return
     */
    IPage<Credit> applyByDistrNm(@Param("page") SearchPage<?> page, @Param("state") Integer state);
}
