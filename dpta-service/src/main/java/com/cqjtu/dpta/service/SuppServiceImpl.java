package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.SuppService;
import com.cqjtu.dpta.dao.entity.Supp;
import com.cqjtu.dpta.dao.mapper.SuppMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class SuppServiceImpl extends ServiceImpl<SuppMapper, Supp> implements SuppService {

}
