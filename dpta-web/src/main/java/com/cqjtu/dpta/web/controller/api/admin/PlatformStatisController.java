package com.cqjtu.dpta.web.controller.api.admin;

import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.web.support.StatisSupport;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/platform/api/statis")
public class PlatformStatisController extends StatisSupport {

    @Resource
    private StatisService statisService;

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
     */
    @GetMapping("top")
    public Result top() {
        SearchHits<MultiGetRequest.Item> hits = search(Top.query);

        return Result.ok(hits);
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
                .subAggregation(AggregationBuilders.sum("comm_amount").script(script));
        static NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withPageable(leastPage())
                .withQuery(queryBuilder)
                .addAggregation(AggregationBuilders.nested("top", "details").subAggregation(termsAggregationBuilder))
                .build();
    }

}
