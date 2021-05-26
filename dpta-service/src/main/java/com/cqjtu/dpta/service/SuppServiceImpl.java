package com.cqjtu.dpta.service;

import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.Supp;
import com.cqjtu.dpta.dao.mapper.SuppMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SuppServiceImpl extends ServiceImpl<SuppMapper, Supp> implements CrudService<Supp> {

}
