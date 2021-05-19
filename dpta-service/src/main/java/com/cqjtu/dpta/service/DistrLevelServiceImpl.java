package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.dao.mapper.DistrLevelMapper;
import com.cqjtu.dpta.api.DistrLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    DistrService distrService;
    @Override
    public void levelUp(Long distrId) {
        // 分销商入驻时间离现在有几个月
        int month = baseMapper.getMonths(distrId);
        // 分销商从入驻至今给平台带来多少收益
        BigDecimal sum = baseMapper.getSumPafP(distrId);
        Distr distr = distrService.getById(distrId);
        QueryWrapper<DistrLevel> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("YIELD");
        // 获取等级列表，按降序排列
        List<DistrLevel> list = baseMapper.selectList(wrapper);
        for (DistrLevel distrLevel : list) {
            // 判断是否符合该等级规则，若符合，则绑定
            if (distrLevel.getPafP().compareTo(sum)!=1 && distrLevel.getSettledM().compareTo(month)<=0) {
                distr.setLevelId(distrLevel.getLevelId());
                break;
            }
        }
        distrService.updateById(distr);
    }
}
