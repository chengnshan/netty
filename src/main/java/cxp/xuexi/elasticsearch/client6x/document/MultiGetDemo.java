package cxp.xuexi.elasticsearch.client6x.document;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
/**
 * Created by ASUS on 2018/4/25.
 */
public class MultiGetDemo {
    public static void main(String[] args) throws Exception{
        TransportClient client= ESUtil.getClient();
        MultiGetResponse mgResponse = client.prepareMultiGet()
                .add("index1","blog","1","2")
                .add("my-index","persion","1","2","2")
                .get();
        for(MultiGetItemResponse response:mgResponse){
            GetResponse rp=response.getResponse();
            if(rp!=null && rp.isExists()){
                System.out.println(rp.getSourceAsString());
            }
        }
    }
}
