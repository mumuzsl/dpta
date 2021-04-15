package com.cqjtu.dpta.api;

import com.cqjtu.dpta.common.vo.CommVo;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
public interface StatisService {
    List<CommVo> topComm(Integer num);
}
