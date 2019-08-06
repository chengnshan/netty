package cxp.xuexi.elasticsearch.client6x.document;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import java.util.Date;
/**
 * Created by ASUS on 2018/4/25.
 */
public class BulkDemo {
    public static void main(String[] args) throws Exception{
        TransportClient client= ESUtil.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );
        //批量执行
        BulkResponse bulkResponse = bulkRequest.get();
        System.out.println(bulkResponse.status());
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("存在失败操作");
        }
    }
}
