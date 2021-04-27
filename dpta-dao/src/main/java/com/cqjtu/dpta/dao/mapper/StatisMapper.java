package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.vo.CommStatisVo;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
public interface StatisMapper {
    /**
     * 获取收入top榜前limit个商品数据
     *
     * @param limit
     * @return
     */
    List<CommStatisVo> topComm(Integer limit);
}
