package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.dao.entity.Distr;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * <p>
 * 授信表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CreditService extends CrudService<Credit> {

    IPage<Distr> pageBySuppAndState(Long suppId, Pageable pageable, String keyword, Integer state);

    IPage<Credit> pageByDistrAndState(Long distrId, Pageable pageable, String keyword, Integer state);

    IPage<Credit> applyBySuppNm(Pageable pageable, String keyword, Integer state);

    IPage<Credit> applyByDistrNm(Pageable pageable, String keyword, Integer state);

    /**
     * 使用授信付款
     *
     * @param id :     授信编码
     * @param amount : 使用金额
     * @param dealId : 订单ID
     * @return 成功返回TRUE，失败返回FALSE
     */
    Boolean useCredit(Long id, BigDecimal amount, Long dealId);

    /**
     * 恢复授信额度
     *
     * @param id:     授信编码
     * @param amount: 恢复的额度
     * @return 成功返回TRUE，失败返回FALSE
     */
    Boolean renewCredit(Long id, Double amount);

    Boolean renewCredit(Long id, BigDecimal amount);


    PageResult findByName(PageQueryUtil pageUtil, String name);

    PageResult findByName1(PageQueryUtil pageUtil, String name);

    PageResult getRecords(PageQueryUtil pageUtil, Long suppId, Long distrId);
}
