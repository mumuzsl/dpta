package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.ResveDService;
import com.cqjtu.dpta.dao.entity.ResveD;
import com.cqjtu.dpta.dao.mapper.ResveDMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预备金明细表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class ResveDServiceImpl extends ServiceImpl<ResveDMapper, ResveD> implements ResveDService {
    /**
     * 获取插入时的ID
     * @return
     */
    public Long getInsertId() {
        return baseMapper.getInsertId();
    }
}
