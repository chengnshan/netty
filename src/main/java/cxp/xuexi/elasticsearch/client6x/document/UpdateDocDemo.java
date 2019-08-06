package cxp.xuexi.elasticsearch.client6x.document;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
/**
 * Created by ASUS on 2018/4/25.
 */
public class UpdateDocDemo {
    public static void main(String[] args)throws Exception{
        TransportClient client= ESUtil.getClient();
        UpdateRequest request=new UpdateRequest();
        request.index("index1")
                .type("blog")
                .id("2")
                .doc(
                        jsonBuilder().startObject()
                                .field("title","单例模式解读")
                                .endObject()
                );
        UpdateResponse response=client.update(request).get();
        //更新成功返回OK，否则返回NOT_FOUND
        System.out.println(response.status());
        //返回被更新文档的类型
        System.out.println(response.getType());
        //返回被更新文档的ID
        System.out.println(response.getId());
        //返回被更新文档的版本信息
        System.out.println(response.getVersion());
        
    }
}
