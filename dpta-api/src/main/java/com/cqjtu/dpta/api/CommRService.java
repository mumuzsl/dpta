package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.api.support.CrudService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * 佣金规则表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CommRService extends CrudService<CommR> {

    IPage<CommR> bindSort(Pageable pageable);

    /**
     * 返回佣金规则
     * @return
     */
    List<CommR> getAllCommR();
}
