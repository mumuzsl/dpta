package com.cqjtu.dpta.api;

import com.cqjtu.dpta.common.vo.CommStatisVo;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
public interface StatisService {
    List<CommStatisVo> topComm(Integer num);
}
