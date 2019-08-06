package cxp.xuexi.rocketMQ.retry_timeout;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by ASUS on 2018/4/5.
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("timeout_producer");
        producer.setNamesrvAddr("192.168.0.133:9876;192.168.0.134:9876");
        producer.start();

        for (int i = 1; i <= 1; i++) {
            try {
                Message msg = new Message("TopicRetryTimeout", // topic
                        "Tag1", // tag
                        ("信息内容" + i).getBytes() // body
                );
                SendResult sendResult = producer.send(msg, 1000);
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        producer.shutdown();
    }
}
