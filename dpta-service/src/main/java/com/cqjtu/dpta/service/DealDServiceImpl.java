package com.cqjtu.dpta.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqjtu.dpta.api.DealDService;
import com.cqjtu.dpta.dao.entity.DealD;
import com.cqjtu.dpta.dao.mapper.DealDMapper;
import org.springframework.stereotype.Service;

@Service
public class DealDServiceImpl  extends ServiceImpl<DealDMapper, DealD> implements DealDService {
}
