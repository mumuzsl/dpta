package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.dao.entity.PafComm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqjtu.dpta.dao.entity.PafSkuStock;
import org.apache.ibatis.annotations.Param;

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
}
