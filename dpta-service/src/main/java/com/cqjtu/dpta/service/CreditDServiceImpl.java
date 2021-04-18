package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.mapper.CreditDMapper;
import com.cqjtu.dpta.api.CreditDService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
