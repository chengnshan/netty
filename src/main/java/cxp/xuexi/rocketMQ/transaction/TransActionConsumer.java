package cxp.xuexi.rocketMQ.transaction;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Created by ASUS on 2018/4/15.
 */
public class TransActionConsumer {
    /**
     * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。
     * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法
     */
    public static void main(String[] args) throws InterruptedException,
            MQClientException {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
        consumer.setNamesrvAddr("192.168.0.133:9876;192.168.0.134:9876");
        consumer.setInstanceName("Consumer");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        /**
         * 订阅指定topic下所有消息<br>
         * 注意：一个consumer对象可以订阅多个topic
         */
        consumer.subscribe("TransactionTest", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
        //  默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg:msgs) {
                        System.out.println("当前线程:"+Thread.currentThread().getName()+" ,quenuID: "+msg.getQueueId()+
                                " ,content: " + new String(msg.getBody()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //如果出现异常,稍后重新发送
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
      //  Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
