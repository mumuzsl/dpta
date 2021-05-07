package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.vo.CreditVo;
import com.cqjtu.dpta.common.vo.DealVo;
import com.cqjtu.dpta.dao.entity.Credit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqjtu.dpta.dao.entity.Distr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    IPage<Credit> applyBySuppNm(SearchPage<?> page, @Param("state") Integer state);

    /**
     * 根据分销商名称和授信状态搜索授信
     *
     * @param page  分页类，包含关键字
     * @param state
     * @return
     */
    IPage<Credit> applyByDistrNm(@Param("page") SearchPage<?> page, @Param("state") Integer state);
    List<CreditVo> getByNm(PageQueryUtil pageUtil, @Param("name") String name);

    List<CreditVo> getByNm1(PageQueryUtil pageUtil, @Param("name") String name);

    List<DealVo> getRecords(PageQueryUtil pageUtil, @Param("suppId") Long suppId, @Param("distrId") Long distrId);
}
