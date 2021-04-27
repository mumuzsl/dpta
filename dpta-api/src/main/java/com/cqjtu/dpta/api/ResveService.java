package com.cqjtu.dpta.api;

import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.api.support.CrudService;

import java.math.BigDecimal;

/**
 * <p>
 * 预备金表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface ResveService extends CrudService<Resve> {

    Boolean useResve (Long distrId,BigDecimal amount,int type);
}
