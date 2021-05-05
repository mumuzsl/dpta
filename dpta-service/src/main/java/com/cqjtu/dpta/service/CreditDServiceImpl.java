package com.cqjtu.dpta.service;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.dao.mapper.CreditDMapper;
import com.cqjtu.dpta.api.CreditDService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 授信明细表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class CreditDServiceImpl extends ServiceImpl<CreditDMapper, CreditD> implements CreditDService {

    @Override
    public Boolean addCreditD(CreditD creditD) {
        if (creditD == null) {
            return false;
        }
        this.save(creditD);
        return true;
    }

    @Override
    public PageResult getRecords(PageQueryUtil pageUtil, Long id) {
        List<CreditD> list = baseMapper.getRecodrs(pageUtil,id);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
