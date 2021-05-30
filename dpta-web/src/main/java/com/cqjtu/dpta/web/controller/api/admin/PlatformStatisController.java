package com.cqjtu.dpta.web.controller.api.admin;

import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.vo.CommStatisVo;
import com.cqjtu.dpta.dao.repository.VisitsRepository;
import com.cqjtu.dpta.web.support.StatisSupport;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/platform/api/statis")
public class PlatformStatisController extends StatisSupport {

    @Resource
    private StatisService statisService;
    @Resource
    private VisitsRepository visitsRepository;
    @Resource
    private PafCommService pafCommService;

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

    @GetMapping("recent/person")
    public Result person(@RequestParam(value = "day", required = false, defaultValue = "7") int day) {
        List<Object> data = new ArrayList<>(day + 1);
        String[] cols = {"date", "访问人次", "访问人数"};
        data.add(cols);

        for (int i = day; i > 0; i--) {
            ArrayList<Object> y = new ArrayList<>(cols.length);
            y.add(DptaUtils.nowMinusDaysStr(i - 1));
            data.add(y);
        }

        for (int i = 0; i < day; i++) {
            int count1 = DptaUtils.nowMinusDays(day - i - 1, visitsRepository::countByDateBetween);
            int count2 = DptaUtils.nowMinusDays(day - i - 1, visitsRepository::countAllByUser);
            List list = (List) data.get(i + 1);
            list.add(count1);
            list.add(count2);
        }

        return Result.ok(data);
    }

    /**
     * 产生收入的TOP10商品的排序，展示商品名称、单价、数量、总金额
     */
    @GetMapping("top")
    public Result top() {
        SearchHits<MultiGetRequest.Item> hits = search(Top.query);

        ParsedNested top = hits.getAggregations().get("top");
        ParsedLongTerms comm = top.getAggregations().get("comm");
        List<? extends Terms.Bucket> buckets = comm.getBuckets();

        List<CommStatisVo> list = new ArrayList<CommStatisVo>(buckets.size());

        buckets.forEach(bucket -> {
            ParsedSum commCount = bucket.getAggregations().get("comm_count");
            ParsedSum commAmount = bucket.getAggregations().get("comm_amount");

            Long id = Long.valueOf(bucket.getKey().toString());
            int count = Double.valueOf(commCount.value()).intValue();

            CommStatisVo vo = new CommStatisVo();
            vo.setCommId(id);
            vo.setAmount(BigDecimal.valueOf(commAmount.value()));
            vo.setCount(count);
            vo.setGoodsVo(pafCommService.commDetail(id));

            pafCommService.updateSalesVolume(id, count);

            list.add(vo);
        });

        return Result.ok(list);
    }

    static class Top {
        static int DEFAULT_LIMTI = 10;
        static Script script = new Script(ScriptType.INLINE,
                "expression",
                "doc['details.count']*doc['details.price']",
                Collections.emptyMap(),
                Collections.emptyMap());
        static TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
                .terms("comm")
                .field("details.id")
                .size(DEFAULT_LIMTI)
                .subAggregation(AggregationBuilders.sum("comm_count").field("details.count"))
                .subAggregation(AggregationBuilders.sum("comm_amount").script(script))
                .order(BucketOrder.aggregation("comm_amount", false));
        static NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withPageable(leastPage())
                .withQuery(queryBuilder)
                .addAggregation(AggregationBuilders.nested("top", "details").subAggregation(termsAggregationBuilder))
                .build();
    }

}
