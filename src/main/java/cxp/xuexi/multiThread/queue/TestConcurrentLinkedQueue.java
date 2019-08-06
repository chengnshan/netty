package cxp.xuexi.multiThread.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ASUS on 2018/5/1.
 */
public class TestConcurrentLinkedQueue {
    public static void main(String[] args){
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
        q.offer("aaa");
        q.offer("bbb");
        q.offer("ccc");
        q.offer("dddd");
        q.add("eee");

        System.out.println(q.poll());
        System.out.println(q.size());
        System.out.println(q.peek());
        System.out.println(q.size());
    }
}
