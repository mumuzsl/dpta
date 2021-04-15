package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.mapper.CommRMapper;
import com.cqjtu.dpta.api.CommRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 佣金规则表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class CommRServiceImpl extends ServiceImpl<CommRMapper, CommR> implements CommRService {

    @Override
    public IPage<CommR> bindSort(Pageable pageable) {
        return baseMapper.bindSort(toPage(pageable));
    }

}
