package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.vo.CommRVo;
import com.cqjtu.dpta.dao.entity.CommR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 佣金规则表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CommRMapper extends BaseMapper<CommR> {
    /**
     * 更加绑定量排序
     *
     * @param page
     * @return
     */
    IPage<CommR> bindSort(Page<?> page);

    List<CommR> getAll(PageQueryUtil queryUtil);

    List<CommR> getByNm(PageQueryUtil queryUtil,@Param("name") String name);

    /**
     * 返回佣金规则
     * @return
     */
    List<CommRVo> getAllCommR();
}
