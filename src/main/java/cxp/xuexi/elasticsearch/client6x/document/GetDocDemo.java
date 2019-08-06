package cxp.xuexi.elasticsearch.client6x.document;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by ASUS on 2018/4/25.
 */
public class GetDocDemo {
    public static void main(String[] args){
        TransportClient client= ESUtil.getClient();
        GetResponse response =client.prepareGet("index1","blog","1").get();
        System.out.println(response.isExists());
        System.out.println(response.getIndex());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getVersion());
        String source=response.getSourceAsString();
        System.out.println(source);
    }
}
