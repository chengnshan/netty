package multiThread.practice;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ASUS on 2018/5/20.
 */
public class Testter extends Thread{
    private static final int count = 99000;
    public static volatile long volLong = 0L;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            volLong++;
        }
        System.out.println(volLong);
    }

    public static void main(String[] args) throws InterruptedException {
        Testter t=new Testter();
        for(int i=0;i<3;i++){
            new Thread(t).start();
        }
        Thread.sleep(2000);
        System.out.println(volLong);
    }

    public static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                    a++;
                    a++;
                }
            }
        });
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
            b+=5;
        }
        thread.join(); //等待线程结束
        long end = System.currentTimeMillis();
        System.out.println("concurrency : " + (end - start) + " ms");
    }

    public static void serial(){
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
            a++;
            a++;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
            b+=5;
        }
        long end = System.currentTimeMillis();
        System.out.println("serial : " + (end - start) + " ms");
    }
}
