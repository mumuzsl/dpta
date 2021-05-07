package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.dao.mapper.RefundRMapper;
import com.cqjtu.dpta.api.RefundRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public PageResult getCommRByName(PageQueryUtil pageUtil, String name) {
        List<RefundR> list = baseMapper.getByNm(pageUtil,name);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Resource
    PafCommService pafCommService;

    @Override
    public int delList(List<Long> ids) {

        int sus = 0;
        for (Long id : ids) {
            QueryWrapper<PafComm> wrapper = new QueryWrapper<>();
            wrapper.eq("refund_id",id);
            int count = pafCommService.count(wrapper);
            if (count == 0) {
                sus += baseMapper.deleteById(id);
            }
        }
        return sus;
    }
}
