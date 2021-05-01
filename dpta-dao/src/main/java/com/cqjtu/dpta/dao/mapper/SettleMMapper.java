package com.cqjtu.dpta.dao.mapper;

import com.cqjtu.dpta.dao.entity.SettleM;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 佣金月结算记录表 Mapper 接口
 * </p>
 *
 * @author nisu
 * @since 2021-04-26
 */
public interface SettleMMapper extends BaseMapper<SettleM> {
    /**
     * 返回平台收益
     */
    Integer platSum(@Param("d") int d);
}
