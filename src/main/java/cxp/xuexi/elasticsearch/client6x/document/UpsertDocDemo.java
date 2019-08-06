package cxp.xuexi.elasticsearch.client6x.document;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
/**
 * Created by ASUS on 2018/4/25.
 * 文档upsert操作：如果文档存在则执行更新操作，否则执行添加操作
 */
public class UpsertDocDemo {
    public static void main(String[] args)throws Exception{
        TransportClient client= ESUtil.getClient();
        IndexRequest request1 =new IndexRequest("index1","blog","1")
                .source(
                        jsonBuilder().startObject()
                                .field("id","1")
                                .field("title","装饰模式")
                                .field("content","动态地扩展一个对象的功能")
                                .field("postdate","2018-02-03 14:38:10")
                                .field("url","csdn.net/79239072")
                                .endObject()
                );
        UpdateRequest request2=new UpdateRequest("index1","blog","1")
                .doc(
                        jsonBuilder().startObject()
                                .field("title","装饰模式解读")
                                .endObject()
                ).upsert(request1);
        UpdateResponse response=client.update(request2).get();
        //upsert操作成功返回OK，否则返回NOT_FOUND
        System.out.println(response.status());
        //返回被操作文档的类型
        System.out.println(response.getType());
        //返回被操作文档的ID
        System.out.println(response.getId());
        //返回被操作文档的版本信息
        System.out.println(response.getVersion());
    }
}
