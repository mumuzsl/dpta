package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.common.vo.CommStatisVo;
import com.cqjtu.dpta.dao.mapper.StatisMapper;
import com.cqjtu.dpta.service.api.OrderDService;
import com.cqjtu.dpta.service.api.PafCommService;
import com.cqjtu.dpta.service.api.StatisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@Service
public class StatisServiceImpl implements StatisService {

    @Resource
    private StatisMapper statisMapper;

    @Resource
    private OrderDService orderDService;

    @Resource
    private PafCommService pafCommService;

    @Override
    public List<CommStatisVo> topComm(Long distrId, Integer limit) {
        return statisMapper.topComm(distrId, limit);
    }
}
