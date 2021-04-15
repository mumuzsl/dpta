package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.dao.mapper.CreditMapper;
import com.cqjtu.dpta.api.CreditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 授信表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class CreditServiceImpl extends ServiceImpl<CreditMapper, Credit> implements CreditService {


    @Override
    public IPage<Credit> applyBySuppNm(Pageable pageable, String keyword, Integer state) {
        SearchPage<Credit> page = SearchPage.toPage(pageable, keyword);
        return baseMapper.applyBySuppNm(page, state);
    }

    @Override
    public IPage<Credit> applyByDistrNm(Pageable pageable, String keyword, Integer state) {
        SearchPage<Credit> page = SearchPage.toPage(pageable, keyword);
        return baseMapper.applyByDistrNm(page, state);
    }
}
