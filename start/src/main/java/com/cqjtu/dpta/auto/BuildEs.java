package com.cqjtu.dpta.auto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.api.OrderIndexService;
import com.cqjtu.dpta.api.OrderService;
import com.cqjtu.dpta.dao.dto.OrderDto;
import com.cqjtu.dpta.dao.repository.OrderIndexRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;

/**
 * author: mumu
 * date: 2021/5/13
 */
@Slf4j
public class BuildEs implements InitializingBean {
    @Resource
    private OrderService orderService;
    @Resource
    private DistrUserService distrUserService;
    @Resource
    private OrderIndexService orderIndexService;
    @Resource
    private OrderIndexRepository orderIndexRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("开始清理索引");
                orderIndexRepository.findAll().forEach(orderIndex -> {
                    if (!orderService.exists(orderIndex.getId())) {
                        orderIndexRepository.deleteById(orderIndex.getId());
                    }
                });
                log.info("索引清理完成");
            }
        }).start();
    }

    public void upload(Long distrId) {
        int size = 100;
        int page = 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "DATM");
        log.info("当前上传: " + distrId);
        while (page > -1) {
            log.info("向ES上传数据: " + page + "页");
            IPage<OrderDto> data = orderService.pageOrderDto(PageRequest.of(page, size, sort), distrId, null);
            if (page <= data.getPages()) {
                log.info("数据总量: " + data.getTotal());
                data.getRecords().forEach(orderIndexService::create);
                page++;
            } else {
                page = -1;
            }
        }
    }

    public void distr() {
        log.info("开始上传数据");
        distrUserService.list().forEach(distrUser -> {
            upload(distrUser.getDistrId());
        });
        log.info("上传完毕");
    }
}
