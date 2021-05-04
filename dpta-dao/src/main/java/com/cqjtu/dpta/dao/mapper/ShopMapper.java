package com.cqjtu.dpta.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商铺表 Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface ShopMapper extends BaseMapper<Shop> {

    IPage<PafComm> getComms(Page<?> page,
                            @Param("distrId") Long distrId,
                            @Param("shopId") Long shopId,
                            @Param("state") Integer state);

}
