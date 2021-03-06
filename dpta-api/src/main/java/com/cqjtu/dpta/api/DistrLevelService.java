package com.cqjtu.dpta.api;

import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.api.support.CrudService;

/**
 * <p>
 * 分销商等级表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface DistrLevelService extends CrudService<DistrLevel> {

    PageResult getList(PageQueryUtil pageUtil);

    void levelUp (Long distrID);
}
