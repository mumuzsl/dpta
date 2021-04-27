package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.api.support.CrudService;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 授信表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CreditService extends CrudService<Credit> {

    IPage<Credit> applyBySuppNm(Pageable pageable, String keyword, Integer state);

    IPage<Credit> applyByDistrNm(Pageable pageable, String keyword, Integer state);

    /**
     * 使用授信付款
     * @param id: 授信编码
     * @param amount: 使用金额
     * @param dealId: 订单ID
     * @return 成功返回TRUE，失败返回FALSE
     */
    Boolean useCredit(Long id,Double amount, Long dealId);

    /**
     * 恢复授信额度
     * @param id: 授信编码
     * @param amount: 恢复的额度
     * @return 成功返回TRUE，失败返回FALSE
     */
    Boolean renewCredit(Long id,Double amount);


}
