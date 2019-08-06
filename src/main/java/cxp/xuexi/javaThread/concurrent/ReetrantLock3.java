package cxp.xuexi.javaThread.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 2018/4/5.
 */
public class ReetrantLock3 implements Runnable {
    public static ReentrantLock lock =new ReentrantLock();
    public void run() {
        try{
            //尝试5秒内获取锁
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread()+" get lock success");
                Thread.sleep(6000);
            }else {
                System.out.println(Thread.currentThread()+" get lock failed");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread())
            lock.unlock();
        }
    }

    public static void main(String[] args){
      /*  ReetrantLock3 reetrantLock3=new ReetrantLock3();
        Thread t1=new Thread(reetrantLock3);
        Thread t2=new Thread(reetrantLock3);
        t1.start();
        t2.start();*/
      byte[] b=null;
      for(int i=0;i<10;i++){
          b=new byte[1*1024*1024];
      }
    }
}
