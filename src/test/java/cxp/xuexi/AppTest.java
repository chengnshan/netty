package cxp.xuexi;

import cxp.xuexi.LengthFieldBasedFrameDecoder.pojo.Message;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    static Logger logger = LogManager.getLogger(AppTest.class.getName());

    @Test
    public void test2() throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        //执行http get请求
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpGet);
//获取请求状态
        System.out.println(response.getStatusLine());
        logger.info(response.getStatusLine());
        //获取返回实体
        HttpEntity entity = response.getEntity();
        System.out.println("Content-Type:" + entity.getContentType());
        //获取网页
        System.out.println(EntityUtils.toString(entity, "utf-8"));
        response.close();
        client.close();
    }

    @Test
    public void test3() {
        String str = "哈哈,我是一条消息";
        Message msg = new Message((byte) 0xAD, 35, str);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(msg);
            os.flush();
            byte[] b = out.toByteArray();
            System.out.println("jdk序列化后的长度： " + b.length);
            os.close();
            out.close();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            byte[] bt = msg.getMsgBody().getBytes();
            buffer.put(msg.getType());
            buffer.putInt(msg.getLength());
            buffer.put(bt);
            buffer.flip();

            byte[] result = new byte[buffer.remaining()];
            buffer.get(result);
            System.out.println("使用二进制序列化的长度：" + result.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        A a1 = new A("a1", "a1");
        A a2 = new A("a2", "a2");
        //    A a2=a1;
        Set set = new HashSet();
        set.add(a1);
        set.add(a2);
        System.out.println(set);
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());
        Map<String, String> map1 = new HashMap(2);
        String aa = map1.put("1", "aa");
        String bb = map1.put("2", "bb");
        String cc = map1.put("1", "cc");
        String dd = map1.put("3", "dd");
        System.out.println(" aa :  " + aa + " bb:  " + bb + " cc:  " + cc + " dd:  " + dd);

        int i = 2 >> 2;//1000   0010
        System.out.println(i);

    }

    @Test
    public void test5() {
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));
        test(d);
    }

    @Test
    public void test6() {
        String str = "15985264646\t\t13856985213\t\t00-25-FA-36-SE-36\t\t1230\t\t24632\t\t200";
        String[] strarr=str.split("\t+");
        System.out.println(Arrays.toString(strarr));

    }

    public void test(String a) {
        new Thread(){
            public void run() {
                System.out.println(a);
            };
        }.start();
    }
}

class A {
    String name;
    String address;

    public A(String name, String address) {
        this.name = name;
        this.address = address;
    }

 /*   @Override
    public int hashCode() {
        return 100;
    }*/

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}
