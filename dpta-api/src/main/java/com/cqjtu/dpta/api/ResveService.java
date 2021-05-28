package com.cqjtu.dpta.api;

import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.ResveD;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * <p>
 * 预备金表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface ResveService extends CrudService<Resve> {

    Boolean useResve(Long distrId, BigDecimal amount, int type);

    Boolean useResve(Long distrId, BigDecimal amount, int type, Consumer<ResveD> consumer);

    Boolean useResve(Long distrId, BigDecimal amount, Long dealId);
}
