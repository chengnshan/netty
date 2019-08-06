package cxp.xuexi.elasticsearch.client6x;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
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
public class ExistsIndexDemo {

    public static void main(String[] args) throws UnknownHostException {
        //1.设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elastic6").build();
        //2.创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.134"), 9320));
        //3.获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        //4.判定索引是否存在
        IndicesExistsResponse response=indicesAdminClient.prepareExists("index1").get();
        System.out.println(response.isExists());
    }
}
