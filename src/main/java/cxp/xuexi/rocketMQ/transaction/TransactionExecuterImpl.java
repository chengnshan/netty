package cxp.xuexi.rocketMQ.transaction;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ASUS on 2018/4/15.
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter {
    /**
     * 线程安全整数，i++或者++i其实是线程不安全的
     */
    private AtomicInteger transactionIndex = new AtomicInteger(1);

    public LocalTransactionState executeLocalTransactionBranch(final Message msg, final Object arg) {
        //  这里的arg就是在Producer那个类中传过来的
        System.out.println(arg);
        System.out.println("msg"+new String(msg.getBody()));

        String tag=msg.getTags();
        if(tag.equals("Transaction1")){
            //这里有一个分阶段提交任务的概念
            System.out.println("这里处理业务逻辑，比如操作数据，失败情况下进行ROLLBACK");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }
}
