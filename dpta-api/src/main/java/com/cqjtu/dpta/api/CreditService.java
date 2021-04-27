package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.common.extension.SearchPage;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.api.support.CrudService;
import org.apache.ibatis.annotations.Param;
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

}
