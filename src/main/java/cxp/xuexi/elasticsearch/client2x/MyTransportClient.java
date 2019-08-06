package cxp.xuexi.elasticsearch.client2x;

/*import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import java.net.InetAddress;*/

/**
 * Created by ASUS on 2018/4/22.
 */
public class MyTransportClient {
  /*  public static void main(String[] args) throws Exception {
    *//*    Client client = TransportClient.builder().build().
                addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.0.134"),9310));*//*

        // 配置信息
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true) //自动嗅探整个ES集群节点
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.0.134"),9310));

        GetResponse getResponse = client.prepareGet().setIndex("test1").setType("student").setId("2").get();
        System.out.println(getResponse.getSourceAsString());
        client.close();
    }*/
}
