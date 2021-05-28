package com.cqjtu.dpta.web.controller.api;

import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.vo.CommStatisVo;
import com.cqjtu.dpta.common.vo.StatisVo;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.repository.VisitsRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/distr/api/statis")
//@Cacheable("statis")
public class StatisController {

    @Resource
    private StatisService statisService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    VisitsRepository visitsRepository;
    @Resource
    private PafCommService pafCommService;

    @GetMapping("recent/person")
    public Result person(@RequestParam(value = "day", required = false, defaultValue = "7") int day,
                         Info info) {
        List<Object> data = new ArrayList<>(day + 1);
        String[] cols = {"date", "访问人次", "访问人数"};
        data.add(cols);

        for (int i = day; i > 0; i--) {
            ArrayList<Object> y = new ArrayList<>(cols.length);
            y.add(DptaUtils.nowMinusDaysStr(i - 1));
            data.add(y);
        }

        for (int i = 0; i < day; i++) {
            int count1 = DptaUtils.nowMinusDays(day - i - 1, (d1, d2) -> visitsRepository.countByDistrIdAndDateBetween(info.id(), d1, d2));
            int count2 = DptaUtils.nowMinusDays(day - i - 1, (d1, d2) -> visitsRepository.countByUser(info.id(), d1, d2));
            List list = (List) data.get(i + 1);
            list.add(count1);
            list.add(count2);
        }

        return Result.ok(data);
    }


//    @GetMapping("recent/person-time")
//    public Result personTime(@RequestParam(value = "day", required = false, defaultValue = "7") int day) {
//        Result<XyVo> result = Result.ok();
//        if (day > 7) {
//            result.setMessage(ResultCodeEnum.DATA_BIG_DAY_ERROR.getMessage());
//        }
//        XyVo xyVo = XyVo.of(day);
//        LocalDateTime now = LocalDateTime.now();
//        for (int i = 0; i < day; i++) {
//            xyVo.getXdata().add(now.minusDays(i + 1).toLocalDate());
//        }
//        List<Object> newYData = new ArrayList<>(day);
//        for (Object o : ydata) {
//            newYData.add((int) o * 3);
//        }
//        xyVo.setYdata(newYData);
//        result.setData(xyVo);
//        return result;
//    }
//
//    @GetMapping("recent/person-number")
//    public Result recent(@RequestParam(value = "day", required = false, defaultValue = "7") int day) {
//        Result<XyVo> result = Result.ok();
//        if (day > 7) {
//            result.setMessage(ResultCodeEnum.DATA_BIG_DAY_ERROR.getMessage());
//        }
//        XyVo xyVo = XyVo.of(day);
//        LocalDateTime now = LocalDateTime.now();
//        for (int i = 0; i < day; i++) {
//            xyVo.getXdata().add(now.minusDays(i + 1).toLocalDate());
//        }
//        xyVo.setYdata(ydata);
//        result.setData(xyVo);
//        return result;
//    }


    @GetMapping("all")
    public Result all() {
        StatisVo statisVo = new StatisVo();
        return Result.ok(statisVo);
    }

    /**
     * 已核销数据查询
     */
    @GetMapping("verified")
    public Result verified() {
        return Result.ok(0);
    }

    /**
     * 未核销数据查询
     */
    @GetMapping("verify")
    public Result verify() {
        return Result.ok(0);
    }

    /**
     * 总收入查询
     */
    @GetMapping("amount")
    public Result amount() {
        return Result.ok(0);
    }

    /**
     * 今日总收入、已核销收入、未核销收入查询
     */
    @GetMapping("today")
    public Result today() {
        return Result.ok(0);
    }

    /**
     * 产生收入的TOP10商品的排序，展示商品名称、单价、数量、总金额
     *
     * @param limit top榜数量限制，默认是10
     */
    @GetMapping("top")
    public Result top(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                      Info info) {
        List<CommStatisVo> vos = statisService.topComm(info.id(), limit);

        vos.forEach(vo -> vo.setGoodsVo(pafCommService.commDetail(vo.getCommId())));

        return Result.ok(vos);
    }

    //    @CachePut("statis")
//    public Result add() {
//        int size = ydata.size();
//        Object o = ydata.get(size - 1);
//        long n = Long.parseLong(String.valueOf(o));
//        ydata.set(size - 1, n);
//        return Result.ok(ydata);
//    }
}
