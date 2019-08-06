package cxp.xuexi.elasticsearch.client6x.document;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
/**
 * Created by ASUS on 2018/4/26.
 */
public class FullTextQuery {
    public static void main(String[] args) {
        QueryUtil util=new QueryUtil("website",5);
        //构造查询对象
        QueryBuilder qb=QueryBuilders.matchQuery(
                "title",
                "centos");
        util.query(qb).print();
    }
}
