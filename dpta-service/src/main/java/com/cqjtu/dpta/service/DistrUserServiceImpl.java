package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.dao.entity.DistrUser;
import com.cqjtu.dpta.dao.mapper.DistrUserMapper;
import org.springframework.stereotype.Service;

/**
 * author: mumu
 * date: 2021/4/15
 */
@Service
public class DistrUserServiceImpl extends ServiceImpl<DistrUserMapper, DistrUser> implements DistrUserService {

}
