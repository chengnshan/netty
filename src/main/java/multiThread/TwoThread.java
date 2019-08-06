package multiThread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 2018/5/17.
 */
public class TwoThread implements Runnable {
    private int state = 0;
    private int num = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    @Override
    public void run() {
        new Thread(new RedThread(), "RedThread").start();
        new Thread(new GreenThread(), "GreenThread").start();
    }

    public static void main(String[] args) throws Exception {
        new Thread(new TwoThread()).start();
    }

    class RedThread implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                while (state < 10) {
                    try {
                        while (num != 1) {
                            condition1.await();
                        }
                        System.out.println(Thread.currentThread().getName() + " is flashing ==> " + state);
                        state++;
                        TimeUnit.SECONDS.sleep(1);// 停留时间，便于从控制台观看
                        num = 2;
                        condition2.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    class GreenThread implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                while (state < 10) {
                    try {
                        while (num != 2) {
                            condition2.await();
                        }
                        System.out.println(Thread.currentThread().getName() + " is flashing ==> " + state);
                        state++;
                        TimeUnit.SECONDS.sleep(1);// 停留时间，便于从控制台观看
                        num = 1;
                        condition1.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
