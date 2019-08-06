package cxp.xuexi.rocketMQ.retry;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;
/**
 * Created by ASUS on 2018/4/6.
 */
public class RetryConsumer {
    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQPushConsumer consume=new DefaultMQPushConsumer("retry_producer");
        consume.setNamesrvAddr("192.168.0.133:9876;192.168.0.134:9876");
        consume.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consume.subscribe("TopicRetry","*");
        consume.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    MessageExt msg = msgs.get(0);
                    String msgbody = new String(msg.getBody(), "utf-8");
                    System.out.println(msgbody + " Receive New Messages: " + msgs);
                    if (msgbody.equals("HelloWorld - RocketMQ4")) {
                        System.out.println("======错误=======");
                        int a = 1 / 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (msgs.get(0).getReconsumeTimes() == 3) {
                        // 该条消息可以存储到DB或者LOG日志中，或其他处理方式
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;// 成功
                    } else {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;// 重试
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consume.start();
        System.out.println("Consumer Started.");
    }
}
