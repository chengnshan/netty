package cxp.xuexi;

import cxp.xuexi.elasticsearch.client6x.aggregations.AggregationUtil;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.junit.Test;

/**
 * Created by ASUS on 2018/4/26.
 */
public class TestAggregationDemo {

    @Test
    public void max() {
        AggregationUtil util = new AggregationUtil("my-index");
        double max = util.max("age");
        System.out.println("max age=" + max);
    }

    @Test
    public void min() {
        AggregationUtil util = new AggregationUtil("my-index");
        double min = util.min("salary");
        System.out.println("min salary=" + min);
    }

    @Test
    public void avg() {
        AggregationUtil util = new AggregationUtil("my-index");
        double avg = util.avg("salary");
        System.out.println("avg salary=" + avg);
    }

    @Test
    public void sum() {
        AggregationUtil util = new AggregationUtil("my-index");
        double sum = util.sum("salary");
        System.out.println("sum salary=" + sum);
    }

    @Test
    public void stats() {
        AggregationUtil util = new AggregationUtil("my-index");
        Stats stats = util.stats("salary");
        System.out.println("min=" + stats.getMin());
        System.out.println("max=" + stats.getMax());
        System.out.println("avg=" + stats.getAvg());
        System.out.println("sum=" + stats.getSum());
        System.out.println("count=" + stats.getCount());
    }

    @Test
    public void extendedStats() {
        AggregationUtil util = new AggregationUtil("my-index");
        ExtendedStats stats = util.extendedStats("salary");
        System.out.println("min=" + stats.getMin());
        System.out.println("max=" + stats.getMax());
        System.out.println("avg=" + stats.getAvg());
        System.out.println("sum=" + stats.getSum());
        System.out.println("count=" + stats.getCount());
        System.out.println("stdDeviation=" + stats.getStdDeviation());
        System.out.println("sumOfSquares=" + stats.getSumOfSquares());
        System.out.println("variance=" + stats.getVariance());
    }

    @Test
    public void cardinality() {
        AggregationUtil util = new AggregationUtil("my-index");
        double cardinality = util.cardinality("salary");
        System.out.println("cardinality=" + cardinality);
    }

    @Test
    public void percentiles() {
        AggregationUtil util = new AggregationUtil("my-index");
        Percentiles percent = util.percentiles("salary");
        for (Percentile p : percent) {
            System.out.printf("percent [%f],value [%f]\n", p.getPercent(), p.getValue());
        }
    }

    @Test
    public void valueCount() {
        AggregationUtil util = new AggregationUtil("my-index");
        double count = util.valueCount("salary");
        System.out.println("count=" + count);
    }

    @Test
    public void terms() {
        AggregationUtil util = new AggregationUtil("my-index");
        Terms terms = util.terms("salary");
        for (Terms.Bucket entry : terms.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }
    }

    @Test
    public void filter() {
        AggregationUtil util = new AggregationUtil("my-index");
        Filter filter = util.filter("gender", "男");
        System.out.println(filter.getDocCount());
    }

    @Test
    public void multiFilter() {
        AggregationUtil util = new AggregationUtil("my-index");
        Filters agg = util.filters("gender", "男", "dep", "bigdata");
        for (Filters.Bucket entry : agg.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }
    }


    @Test
    public void range() {
        AggregationUtil util = new AggregationUtil("my-index");
        Range agg = util.range("salary", 10000, 20000);
        for (Range.Bucket entry : agg.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }
    }

    @Test
    public void dateRange() {
        AggregationUtil util = new AggregationUtil("website");
        Range agg = util.dateRange("postdate", "now-12M/M", "now-12M/M");
        for (Range.Bucket entry : agg.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }
    }
}
