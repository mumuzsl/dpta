package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.CommRService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.common.vo.CommRVo;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.mapper.CommRMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public PageResult getCommRPage(PageQueryUtil pageQueryUtil) {
        List<CommR> list = baseMapper.getAll(pageQueryUtil);
        PageResult pageResult = new PageResult(list, list.size(), pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public PageResult getCommRByName(PageQueryUtil pageQueryUtil, String name) {
        List<CommR> list = baseMapper.getByNm(pageQueryUtil,name);
        PageResult pageResult = new PageResult(list, list.size(), pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Resource
    PafCommService pafCommService;
    @Override
    public int delList(List<Long> ids) {
        int sus = 0;
        for (Long id : ids) {
            QueryWrapper<PafComm> wrapper = new QueryWrapper<>();
            wrapper.eq("r_comm_id",id);
            int count = pafCommService.count(wrapper);
            if (count == 0) {
                CommR commR = baseMapper.selectById(id);
                commR.setDeleted(1);
                sus += baseMapper.updateById(commR);
            }
        }
        return sus;
    }

    @Override
    public PageResult getPafComm(PageQueryUtil pageUtil, Long id) {
        QueryWrapper<PafComm> wrapper = new QueryWrapper<>();
        wrapper.eq("r_comm_id",id);
        List<PafComm> list = pafCommService.list(wrapper);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<CommRVo> getAllCommR() {
        return baseMapper.getAllCommR();
    }

}
