package com.cqjtu.dpta.service.api;

import com.cqjtu.dpta.dao.common.vo.CommStatisVo;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
public interface StatisService {
    List<CommStatisVo> topComm(Long distrId, Integer num);
}
