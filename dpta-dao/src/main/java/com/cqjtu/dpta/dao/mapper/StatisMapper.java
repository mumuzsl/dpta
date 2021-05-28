package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.vo.CommStatisVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
public interface StatisMapper {
    /**
     * 获取收入top榜前limit个商品数据
     */
    List<CommStatisVo> topComm(@Param("distrId") Long distrId,
                               @Param("limit") Integer limit);
}
