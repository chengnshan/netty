package cxp.xuexi.multiThread.queue;

import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * Created by ASUS on 2018/5/1.
 */
public class TestDelayQueue implements Runnable {
    private DelayQueue<WangMin> queue = new DelayQueue<WangMin>();
    public boolean yinye = true;

    public void shangji(String name, String id, int money) {
        WangMin man = new WangMin(name, id, 1000 * money + System.currentTimeMillis());
        System.out.println(new Date()+"网名" + man.getName() + " 身份证" + man.getId() + "交钱" + money + "块，开始上机...");
        this.queue.add(man);
    }

    public void xiaji(WangMin man) {
        System.out.println(new Date()+"网名" + man.getName() + " 身份证" + man.getId() + "时间到下机...");
    }

    public static void main(String[] args) {
        try {
            System.out.println("网吧开始营业");
            TestDelayQueue siyu = new TestDelayQueue();
            Thread shangwang = new Thread(siyu);
            shangwang.start();

            siyu.shangji("路人甲", "123", 1);
            siyu.shangji("路人乙", "234", 10);
            siyu.shangji("路人丙", "345", 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (yinye) {
            try {
                //当执行take操作时候并不直接获取对象，等到延迟时间到了才拿出对象
                WangMin man = queue.take();
                xiaji(man);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
