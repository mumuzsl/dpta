package com.cqjtu.dpta.service;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.dao.entity.TaxR;
import com.cqjtu.dpta.dao.mapper.DistrLevelMapper;
import com.cqjtu.dpta.api.DistrLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分销商等级表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class DistrLevelServiceImpl extends ServiceImpl<DistrLevelMapper, DistrLevel> implements DistrLevelService {

    @Override
    public PageResult getList(PageQueryUtil pageUtil) {
        List<DistrLevel> list = baseMapper.getList(pageUtil);
        PageResult pageResult = new PageResult(list, list.size(), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
