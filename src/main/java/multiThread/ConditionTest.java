package multiThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 2018/5/17.
 */
public class ConditionTest {
    private static int state = 0;

    /**
     * @param args
     */
    public static void main(String[] args) {

        final ReentrantLock lock = new ReentrantLock();

        // thread1
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (state <= 30) {
                    try {
                        // 加锁
                        lock.lock();
                        if (state % 3 == 0) {
                            System.out.print("1");
                            state++;
                        }
                    }
                    finally {
                        lock.unlock();
                    }

                }
            }
        });

        // thread2
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (state <= 30) {
                    try {
                        // 加锁
                        lock.lock();
                        if (state % 3 == 1) {
                            System.out.print("2");
                            state++;
                        }
                    }
                    finally {
                        lock.unlock();
                    }
                }
            }
        });

        // thread3
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (state <= 30) {
                    try {
                        // 加锁
                        lock.lock();
                        if (state % 3 == 2) {
                            System.out.println("3");
                            state++;
                        }
                    }
                    finally {
                        lock.unlock();
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

   /* public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("当前线程: " + Thread.currentThread().getName() + "拿到锁了!");
                System.out.println(Thread.currentThread().getName() + "等待信号");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + "拿到信号");
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("当前线程: " + Thread.currentThread().getName() + "拿到锁了!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "发出信号");
                condition.signal();
                lock.unlock();
            }
        }, "t2").start();
    }*/
}
