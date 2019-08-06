package cxp.xuexi.elasticsearch.client6x.wordquery;

import cxp.xuexi.elasticsearch.client6x.document.QueryUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by ASUS on 2018/4/26.
 */
public class RangQuery {
    public static void main(String[] args) {
        QueryUtil util = new QueryUtil("website", 5);
        //构造查询对象
        QueryBuilder qb = QueryBuilders.rangeQuery("postdate").from("2017-01-01").to("2017-12-31").format("yyyy-MM-dd");
        util.query(qb).print();
    }
}
