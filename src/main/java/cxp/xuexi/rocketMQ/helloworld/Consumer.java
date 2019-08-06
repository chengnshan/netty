package cxp.xuexi.rocketMQ.helloworld;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ASUS on 2018/4/5.
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQPushConsumer consume = new DefaultMQPushConsumer("quickstart_consumer");
        consume.setNamesrvAddr("192.168.0.133:9876;192.168.0.134:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consume.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consume.subscribe("TopicTest", "*");
        /*拉消息本地队列缓存消息最大数：默认1000*/
        consume.setPullThresholdForQueue(1000);
        /*批量消费，一次消费多少条消息：默认1条*/
        consume.setConsumeMessageBatchMaxSize(1);
        /*批量拉消息，一次最多拉多少条，默认32条*/
        consume.setPullBatchSize(32);
        /*消息拉取线程每隔多久拉取一次，默认为0*/
        consume.setPullInterval(0);
        consume.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
                for(MessageExt me:msgs){
                    try {
                        System.out.println(new String(me.getBody(),"utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consume.start();
        System.out.println("Consumer Started.");
    }
}
