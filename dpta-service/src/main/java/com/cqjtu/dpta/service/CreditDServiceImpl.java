package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.CreditDService;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.mapper.CreditDMapper;
import org.springframework.data.domain.Pageable;
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
        List<CreditD> list = baseMapper.getRecodrs(pageUtil, id);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public IPage<CreditD> pageByDistrAndType(Pageable pageable, Long distrId, Integer type) {
        return baseMapper.pageByDistrAndType(toPage(pageable), distrId, type);
    }
}
