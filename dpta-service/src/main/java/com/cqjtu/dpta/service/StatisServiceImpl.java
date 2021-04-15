package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.vo.CommVo;
import com.cqjtu.dpta.dao.mapper.StatisMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<CommVo> topComm(Integer limit) {
        return statisMapper.topComm(limit);
    }
}
