package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.dao.entity.DealD;
import com.cqjtu.dpta.dao.entity.PayM;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/14
 */
public interface DealService extends CrudService<Deal> {
    IPage<Deal> pageBySuppAndDistr(Pageable pageable, Long suppId, Long distrId);

    /**
     * 分销商对订单进行付款
     * @param deal_id 订单编码
     * @return
     */
    PayM payDeal(Long deal_id);

    /**
     * 根据订单编码获取订单明细
     * @param deal_id
     * @return
     */
    List<DealD> getDealDByDealId(Long deal_id);
}
