package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.dao.mapper.RefundRMapper;
import com.cqjtu.dpta.api.RefundRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 退款规则表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class RefundRServiceImpl extends ServiceImpl<RefundRMapper, RefundR> implements RefundRService {

    @Override
    public IPage<RefundR> bindSort(Pageable pageable) {
        return baseMapper.bindSort(toPage(pageable));
    }
}
