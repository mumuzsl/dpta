package com.cqjtu.dpta.api;

import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.common.vo.GoodsVo;
import com.cqjtu.dpta.dao.entity.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 平台-商品表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface PafCommService extends CrudService<PafComm> {
    /**
     * @param comm_id 商品编码
     * @param sku_id
     * @return
     */
    PafSkuStock getByCommIdAndSkuId(Long comm_id, Long sku_id);

    /**
     * 返回销售量前十商品
     *
     * @return
     */
    List<ShopTop> getShopTop();

    /**
     * 返回分销商收入前十
     */
    List<DistrTop> getDistrTop();

    /**
     * 通过分销商名字返回最大销售量的商品
     */
    String getMaxShop(@Param("d") String d);

    /**
     * 返回分销商前2店铺名
     *
     * @param d
     */
    List<DistrSumM> getDistrSumM(@Param("d") String d);

    /**
     * 返回分销商最后2名店铺名
     *
     * @param d
     */
    List<DistrSumM> getDistrSumN(@Param("d") String d);

    /**
     * 返回每年的收益
     */
    BigDecimal getDistrSum(@Param("d") String d, @Param("d") int b);

    /**
     * 返回数据库中所有的店铺结算金额的时间
     *
     * @return
     */
    List<Date> getAllDate();

    /**
     * 返回预测的销售额
     */
    List<ShopPredict> getSPredict();

    GoodsVo commDetail(Long id);

    boolean updateSalesVolume(Long id, Integer count);
}
