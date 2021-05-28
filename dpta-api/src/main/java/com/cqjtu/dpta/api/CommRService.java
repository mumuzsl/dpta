package com.cqjtu.dpta.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.PageResult;
import com.cqjtu.dpta.common.vo.CommRVo;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.api.support.CrudService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * 佣金规则表 服务类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
public interface CommRService extends CrudService<CommR> {

    IPage<CommR> bindSort(Pageable pageable);

    PageResult getCommRPage(PageQueryUtil pageQueryUtil);

    PageResult getCommRByName(PageQueryUtil pageQueryUtil,String name);

    int delList(List<Long> ids);

    PageResult getPafComm(PageQueryUtil pageUtil, Long id);

    /**
     * 返回佣金规则
     * @return
     */
    List<CommRVo> getAllCommR();
}
