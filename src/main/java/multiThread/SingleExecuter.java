package multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ASUS on 2018/5/17.
 */
public class SingleExecuter {
    public static void main(String[] args) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 5) {
                    System.out.println("A" + i);
                    i++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 5) {
                    System.out.println("B" + i);
                    i++;
                }
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 5) {
                    System.out.println("C" + i);
                    i++;
                }
            }
        }, "t3");

        singleThreadExecutor.submit(t1);
        singleThreadExecutor.submit(t2);
        singleThreadExecutor.submit(t3);
        singleThreadExecutor.shutdown();
    }
}
