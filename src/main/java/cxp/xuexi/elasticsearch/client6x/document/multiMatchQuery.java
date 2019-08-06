package cxp.xuexi.elasticsearch.client6x.document;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by ASUS on 2018/4/26.
 */
public class multiMatchQuery {
    public static void main(String[] args) {
        QueryUtil util = new QueryUtil("website", 5);
        QueryBuilder qb = QueryBuilders.multiMatchQuery("centos", "title", "abstract");
        util.query(qb).print();
    }
}
