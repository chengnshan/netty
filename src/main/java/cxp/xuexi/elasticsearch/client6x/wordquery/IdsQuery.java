package cxp.xuexi.elasticsearch.client6x.wordquery;

import cxp.xuexi.elasticsearch.client6x.document.QueryUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by ASUS on 2018/4/26.
 */
public class IdsQuery {
    public static void main(String[] args) {
        QueryUtil util = new QueryUtil("website", 2);
        //构造查询对象
        QueryBuilder qb = QueryBuilders.idsQuery().addIds("1", "3");
        util.query(qb).print();
    }
}
