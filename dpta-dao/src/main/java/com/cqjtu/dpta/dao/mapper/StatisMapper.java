package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.common.vo.CommVo;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
public interface StatisMapper {
    List<CommVo> topComm(Integer limit);
}
