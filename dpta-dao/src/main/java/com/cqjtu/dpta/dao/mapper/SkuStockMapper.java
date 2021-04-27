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
    int lockStock(@Param("id") long id, @Param("num") int num);

    int subtractStock(@Param("pcid") long pcid, @Param("sid") long sid, @Param("num") int num);

    int freeStock(@Param("pcid") long pcid, @Param("sid") long sid, @Param("num") int num);
}
