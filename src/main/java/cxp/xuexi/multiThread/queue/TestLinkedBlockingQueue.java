package cxp.xuexi.multiThread.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ASUS on 2018/5/1.
 */
public class TestLinkedBlockingQueue {
    public static void main(String[] args) {
        //阻塞队列 无界
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();
        q.offer("aa");
        q.offer("bb");
        q.offer("cc");
        q.add("dd");
        System.out.println(q.size());
        for (Iterator iterator = q.iterator(); iterator.hasNext(); ) {
            String string = (String) iterator.next();
            System.out.println(string);
        }

        List<String> list = new ArrayList<String>();
        System.out.println(q.drainTo(list, 2));
        System.out.println(list.size());
        for (String string : list) {
            System.out.println(string);
        }
    }
}
