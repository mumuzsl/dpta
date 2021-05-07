package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.dao.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 平台-商品表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface PafCommMapper extends BaseMapper<PafComm> {

    /**
     *
     * @param comm_id 商品编码
     * @param sku_id
     * @return
     */
    PafSkuStock getByCommIdAndSkuId(@Param("comm_id") Long comm_id, @Param("sku_id") Long sku_id);

    /**
     * 返回销售量前十商品
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
     */
    List<DistrSumM> getDistrSumM(@Param("d") String d);

    /**
     * 返回分销商最后2名店铺名
     */
    List<DistrSumM> getDistrSumN(@Param("d") String d);

    /**
     * 返回每年的收益
     */
    BigDecimal getDistrSum(@Param("d") String d,@Param("b") int b);

    /**
     * 返回数据库中所有的店铺结算金额的时间
     * @return
     */
    List<Date> getAllDate();
}
