package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.DealService;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.dao.mapper.DealMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * author: mumu
 * date: 2021/4/14
 */
@Service
public class DealServiceImpl extends ServiceImpl<DealMapper, Deal> implements DealService {

    @Override
    public IPage<Deal> pageBySuppAndDistr(Pageable pageable, Long suppId, Long distrId) {
        return baseMapper.selectBySuppAndDistr(toPage(pageable), suppId, distrId);
    }
}
