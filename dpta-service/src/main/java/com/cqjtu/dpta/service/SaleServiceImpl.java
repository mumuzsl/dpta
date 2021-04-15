package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.Sale;
import com.cqjtu.dpta.dao.mapper.SaleMapper;
import com.cqjtu.dpta.api.SaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商铺销售表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class SaleServiceImpl extends ServiceImpl<SaleMapper, Sale> implements SaleService {

}
