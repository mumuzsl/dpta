package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.api.support.CrudService;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 退款规则表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface RefundRService extends CrudService<RefundR> {

    IPage<RefundR> bindSort(Pageable pageable);
}
