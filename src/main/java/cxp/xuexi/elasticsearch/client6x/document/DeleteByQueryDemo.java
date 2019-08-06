package cxp.xuexi.elasticsearch.client6x.document;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
/**
 * Created by ASUS on 2018/4/26.
 */
public class DeleteByQueryDemo {
    public static void main(String[] args){
        TransportClient client= ESUtil.getClient();
        BulkByScrollResponse response =DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("title", "模式"))
                .source("index1")//设置索引名称
                .get();
        //被删除文档数目
        long deleted = response.getDeleted();
        System.out.println(deleted);
    }
}
