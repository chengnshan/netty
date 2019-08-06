package cxp.xuexi.elasticsearch.client6x.cluster;

import cxp.xuexi.elasticsearch.client6x.ESUtil;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;

/**
 * Created by ASUS on 2018/4/29.
 */
public class ClusterAdminDemo {
    public static void main(String[] args) {
        ClusterHealthResponse healths = ESUtil.getClusterAdminClient().prepareHealth().get();
        String clusterName = healths.getClusterName();
        System.out.println("clusterName=" + clusterName);
        int numberOfDataNodes = healths.getNumberOfDataNodes();
        System.out.println("numberOfDataNodes=" + numberOfDataNodes);
        int numberOfNodes = healths.getNumberOfNodes();
        System.out.println("numberOfNodes=" + numberOfNodes);

        for (ClusterIndexHealth health : healths.getIndices().values()) {
            String index = health.getIndex();
            int numberOfShards = health.getNumberOfShards();
            int numberOfReplicas = health.getNumberOfReplicas();
            System.out.printf("index=%s,numberOfShards=%d,numberOfReplicas=%d\n", index, numberOfShards, numberOfReplicas);
            ClusterHealthStatus status = health.getStatus();
            System.out.println(status.toString());
        }
    }
}
