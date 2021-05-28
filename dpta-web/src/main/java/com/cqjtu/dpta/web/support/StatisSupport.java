package com.cqjtu.dpta.web.support;

import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.ParsedDateRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * author: mumu
 * date: 2021/5/24
 */
public class StatisSupport {

    @Resource(name = "elasticsearchTemplate")
    protected ElasticsearchOperations elasticsearchOperations;

    public static final String NOW = "now+8h";
    public static final String FORMAT = "yyyy-MM-dd";
    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    protected final NativeSearchQueryBuilder DEFAULT_QUERY = queryBuilder(7);

    public NativeSearchQueryBuilder queryBuilder(int day) {
        String from = NOW + "-" + (day - 1) + "d/d";
        return new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 1))
                .addAggregation(
                        AggregationBuilders
                                .dateRange("statis")
                                .field("datm")
                                .format(FORMAT)
                                .timeZone(ZONE_ID)
                                .addRange(from, NOW)
                                .subAggregation(
                                        AggregationBuilders
                                                .terms("state")
                                                .field("state")
                                                .minDocCount(0)
                                                .subAggregation(
                                                        AggregationBuilders
                                                                .dateHistogram("date")
                                                                .field("datm")
                                                                .calendarInterval(DateHistogramInterval.DAY)
                                                                .format("yyyy-MM-dd")
                                                                .minDocCount(0)
                                                                .keyed(true)
                                                                .extendedBounds(new ExtendedBounds(from, NOW))
                                                )
                                )
                );
    }

    protected NativeSearchQuery build(QueryBuilder builder) {
        return DEFAULT_QUERY.withQuery(builder).build();
    }

    protected NativeSearchQuery build() {
        return DEFAULT_QUERY.build();
    }

    protected SearchHits<MultiGetRequest.Item> search(Query query) {
        return elasticsearchOperations.search(query, MultiGetRequest.Item.class, IndexCoordinates.of("order"));
    }

    protected Result recent(int day, Query query) {
        SearchHits<MultiGetRequest.Item> hits = elasticsearchOperations.search(query, MultiGetRequest.Item.class, IndexCoordinates.of("order"));
        ParsedDateRange statis = hits.getAggregations().get("statis");
        ParsedLongTerms state = statis.getBuckets().get(0).getAggregations().get("state");
        List<? extends Terms.Bucket> stateBuckets = state.getBuckets();

        List<Object> data = new ArrayList<>(day + 1);
        List<Object> headRow = new ArrayList<>(stateBuckets.size() + 1);

        headRow.add("date");
        data.add(headRow);

        for (int i = day - 1; i >= 0; i--) {
            String dayStr = DptaUtils.nowMinusDaysStr(i);
            ArrayList<Object> objects = new ArrayList<>(stateBuckets.size());
            objects.add(dayStr);
            data.add(objects);
        }

        for (Terms.Bucket stateBucket : stateBuckets) {
            ParsedDateHistogram dateHistogram = stateBucket.getAggregations().get("date");
            for (int i = 0; i < day; i++) {
                Histogram.Bucket bucket = dateHistogram.getBuckets().get(i);
                ((List) data.get(i + 1)).add(bucket.getDocCount());
            }

            headRow.add(OrderState.valueOf(Integer.parseInt(stateBucket.getKey().toString())).label());
        }

        return Result.ok(data);
    }


    protected String statisName(String name) {
        return name + "_statis";
    }

    protected String amountName(String name) {
        return name + "_amount";
    }

    protected SumAggregationBuilder amount(String name) {
        return AggregationBuilders
                .sum(amountName(name))
                .field("amount");
    }

    protected DateRangeAggregationBuilder dateh(String name, String from) {
        return AggregationBuilders
                .dateRange(statisName(name))
                .field("datm")
                .addRange(from, NOW)
                .format(FORMAT)
                .timeZone(ZONE_ID)
                .subAggregation(amount(name));
    }

    protected double post(SearchHits<MultiGetRequest.Item> hits, String name) {
        ParsedDateRange dateRange = hits.getAggregations().get(statisName(name));
        Range.Bucket bucket = dateRange.getBuckets().get(0);
        ParsedSum amount = bucket.getAggregations().get(amountName(name));
        return amount.getValue();
    }


    public static Pageable leastPage() {
        return PageRequest.of(0, 1);
    }

    protected NativeSearchQueryBuilder statisQueryBuilder() {
        return new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 1))
                .addAggregation(dateh("day", NOW + "/d"))
                .addAggregation(dateh("month", NOW + "/M"))
                .addAggregation(amount("all"));
    }

    protected static final QueryBuilder queryBuilder =
            QueryBuilders
                    .boolQuery()
                    .filter(QueryBuilders
                            .rangeQuery("state")
                            .gte("1"));
}
