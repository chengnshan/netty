package cxp.xuexi.elasticsearch.client6x;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * Created by ASUS on 2018/4/25.
 */
public class CreateIndexDemo {
    public static void main(String[] args) throws UnknownHostException {
        //1.设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elastic6").build();
        //2.创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.134"), 9320));
        //3.获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        //4.创建索引
        CreateIndexResponse ciReponse=indicesAdminClient.prepareCreate("index1").get();
        System.out.println(ciReponse.isAcknowledged());

    }
}
