package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.dao.entity.SkuStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
public interface SkuStockMapper extends BaseMapper<SkuStock> {
    Integer lockStock(@Param("scid") long id, @Param("num") int num);

    Integer subtractStock(@Param("scid") long id, @Param("num") int num);

    Integer freeStock(@Param("scid") long id, @Param("num") int num);
}
