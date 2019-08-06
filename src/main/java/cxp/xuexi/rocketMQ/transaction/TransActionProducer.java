package cxp.xuexi.rocketMQ.transaction;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * Created by ASUS on 2018/4/15.
 */
public class TransActionProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        //这里需要使用的是TransactionMQProducer
        TransactionMQProducer producer = new TransactionMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.0.133:9876;192.168.0.134:9876");
        //事务回查最小并发数
        producer.setCheckThreadPoolMinSize(2);
        //事务回查最大并发数
        producer.setCheckThreadPoolMaxSize(2);
        //队列数
        producer.setCheckRequestHoldMax(2000);
        //broker回查接口,该功能目前已无效,但是必须创建,否则报错
        producer.setTransactionCheckListener(new TransactionCheckListener() {

            public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
                System.out.printf(" broker回查接口，消息体为：" + msg.getBody() + "%n");
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();

        //本地业务逻辑执行封装在该类中
        TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();

        for (int i = 1; i <= 1; i++) {

            long start = System.currentTimeMillis();
            try {

                Message msg =new Message("TransactionTest", "Transaction"+i, "KEY" + i,
                        ("Hello RocketMQ -->" + i).getBytes());
                /**
                 * msg 具体的消息
                 * tranExecuter 本地业务逻辑都封装在该类中
                 * 最后一个参数,可以传递给tranExecuter中
                 */
                SendResult sendResult = producer.sendMessageInTransaction(msg, tranExecuter, "tq");
                System.out.printf("%s%n", sendResult);
                switch (sendResult.getSendStatus()) {
                    case SEND_OK: {
                        System.out.println("事务消息执行完成");
                    }
                    break;
                    case FLUSH_DISK_TIMEOUT:
                    case FLUSH_SLAVE_TIMEOUT:
                    case SLAVE_NOT_AVAILABLE:
                        System.out.println("事务消息执行失败");
                        break;
                    default:
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        producer.shutdown();
    }
}
