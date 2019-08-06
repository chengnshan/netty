package multiThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 2018/5/17.
 */
public class ThreeThread {
    private int number = 1;//当前正在执行线程的标记
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop) {
        lock.lock();
        try {
            //判断number
            if (number != 1) {
                condition1.await();
            }
                System.out.println(Thread.currentThread().getName() + "\t"  + "\t" + totalLoop);
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        lock.lock();
        try {
            //判断number
            if (number != 2) {
                condition2.await();
            }
                System.out.println(Thread.currentThread().getName() + "\t" + "\t" + totalLoop);
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        lock.lock();
        try {
            //判断number
            if (number != 3) {
                condition3.await();
            }
                System.out.println(Thread.currentThread().getName() + "\t" + "\t" + totalLoop);
            System.out.println("-----------------------------------------------");
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] argsd) {
        ThreeThread at = new ThreeThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    at.loopA(i);
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    at.loopB(i);
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    at.loopC(i);

                }
            }
        }, "C").start();
    }
}
