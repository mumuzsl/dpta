package com.cqjtu.dpta.service;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.dao.entity.TaxR;
import com.cqjtu.dpta.dao.mapper.TaxRMapper;
import com.cqjtu.dpta.api.TaxRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 税费规则表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class TaxRServiceImpl extends ServiceImpl<TaxRMapper, TaxR> implements TaxRService {

    /**
     * 根据个人所得获取对应的税费规则
     *
     * @param amount
     * @return
     */
    @Override
    public TaxR getTaxR(BigDecimal amount) {
        return baseMapper.getTaxR(amount);
    }

    @Override
    public PageResult getList(PageQueryUtil pageUtil) {
        List<TaxR> list = baseMapper.getList(pageUtil);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
