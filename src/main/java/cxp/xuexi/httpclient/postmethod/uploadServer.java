package cxp.xuexi.httpclient.postmethod;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by ASUS on 2018/3/8.
 */
public class uploadServer {
    /**
     * 这个例子展示了如何执行请求包含一个多部分编码的实体
     * 模拟表单提交
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //要上传的文件的路径
            String filePath = "D:\\dowload\\触发_2.jpg";
            String filePath1 = "D:\\dowload\\[www.java1234.com].mp4";
            //把一个普通参数和文件上传给下面这个地址    是一个servlet
            HttpPost httpPost = new HttpPost("http://localhost:8089/multiUploadFile2");
            httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
            httpPost.setHeader("Connection","keep-alive");
            //把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(filePath));
            FileBody bin1 = new FileBody(new File(filePath1));
            //普通字段  重新设置了编码方式
            StringBody comment = new StringBody("这里是一个评论", ContentType.create("text/plain", Consts.UTF_8));
            //StringBody comment = new StringBody("这里是一个评论", ContentType.TEXT_PLAIN);

            StringBody name = new StringBody("王五", ContentType.create("text/plain", Consts.UTF_8));
            StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.RFC6532)
                    .addPart("file", bin)//相当于<input type="file" name="media"/>
                    .addPart("file1", bin1)//相当于<input type="file" name="media"/>
                    .addPart("comment", comment)
                    .addPart("username", name)//相当于<input type="text" name="username" value=name>
                    .addPart("password", password)
                    .build();

            httpPost.setEntity(reqEntity);

            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            //发起请求   并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                System.out.println("----------------------------------------");
                //打印响应状态
                System.out.println(response.getStatusLine());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //打印响应长度
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    //打印响应内容
                    System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                }
                //销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

}
