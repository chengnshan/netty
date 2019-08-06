package cxp.xuexi.elasticsearch.client6x;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Created by ASUS on 2018/4/23.
 */
public class TransportClient6x {
    public static void main(String[] args) throws UnknownHostException {
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "cluster-elastic6").build();
        // 创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.0.133"), 9300));
        // 搜索数据
        GetResponse response = client.prepareGet("student", "student", "1").execute().actionGet();
        // 输出结果
        System.out.println(response.getSourceAsString());
        // 关闭client
        client.close();
    }
}
