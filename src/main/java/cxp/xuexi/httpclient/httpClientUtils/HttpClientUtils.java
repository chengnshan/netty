package cxp.xuexi.httpclient.httpClientUtils;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ASUS on 2018/3/11.
 */
public class HttpClientUtils {

    private static final String CONTENT_CHARSET = "UTF-8";
    private static RequestConfig requestConfig = null;

    static {
        requestConfig =
                RequestConfig.custom()
                        .setSocketTimeout(10000)
                        .setConnectTimeout(10000)
                        .setConnectionRequestTimeout(5000)
                        .build();
    }

    public static String httpGet(String url, Map<String, String> map) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients
                .custom().setDefaultRequestConfig(requestConfig)
                .build();
        HttpGet httpGet = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (map != null) {
                Set<Map.Entry<String, String>> entrySet = map.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }

            httpGet = new HttpGet(uriBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpGet.setHeader("Accept", "application/json;charset=" + CONTENT_CHARSET);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        httpGet.setHeader("Content-type", "application/json;charset=" + CONTENT_CHARSET);
        httpGet.setHeader("Connection", "Close");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求方式一:采用请求头为Json数据格式，服务器端采用对象接收并配注解@RequestBody
     *
     * @param url
     * @param map
     * @return
     */
    public static String httpPost(String url, Map<String, Object> map) {
        String result = null;

        CloseableHttpClient httpClient = HttpClients
                .custom().setDefaultRequestConfig(requestConfig)
                .build();
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(JSON.toJSONString(map), Charset.forName("UTF-8"));
        } catch (UnsupportedCharsetException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json;charset=" + CONTENT_CHARSET);
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        httpPost.setHeader("Content-type", "application/json;charset=" + CONTENT_CHARSET);
        httpPost.setHeader("Connection", "Close");
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpPost.releaseConnection();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求方式二：采用表单形式提交，服务器端用对象接收，不用注解
     *
     * @param url
     * @param map
     * @return
     */
    public static String httpPost_header(String url, Map<String, String> map) {
        String result = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients
                .custom().setDefaultRequestConfig(requestConfig)
                .build();

        List<NameValuePair> list = new ArrayList<>();
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, String> param : map.entrySet()) {
                list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Accept", "application/json;charset=" + CONTENT_CHARSET);
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=" + CONTENT_CHARSET);
        httpPost.setHeader("Connection", "Close");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list,"Utf-8"));
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpPost.releaseConnection();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求方式三：采用表单形式提交，服务器端用多个包装类型属性接收
     * @param url
     * @param map
     * @return
     */
    public static String httpPost_form(String url, Map<String, String> map) {
        String result = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients
                .custom().setDefaultRequestConfig(requestConfig)
                .build();

        List<NameValuePair> list = new ArrayList<>();
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, String> param : map.entrySet()) {
                list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Accept", "application/json;charset=" + CONTENT_CHARSET);
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=" + CONTENT_CHARSET);
        httpPost.setHeader("Connection", "Close");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list,"Utf-8"));
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpPost.releaseConnection();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
    /*    Map<String, String> map1 = new HashMap<String, String>();
        map1.put("username", "张三");
        map1.put("password", "abcdefg");
        String result1 = HttpClientUtils.httpGet("http://localhost:8011/boot1/test1/findProductListHttp", map1);
        System.out.println(result1);*/

        Map<String, Object> map2 = new HashMap<String, Object>();
      /*  map2.put("remark", "abcde");
        map2.put("pname", "张三丰");
        map2.put("price", "12.01");
        map2.put("productDate", new Timestamp(new Date().getTime()));*/
        map2.put("id", "112");
        map2.put("name", "我是采用JSON格式数据,服务器用注解对象接收。");
        map2.put("price", "4500");
        String result2 = HttpClientUtils.httpPost("http://192.168.0.104:8080/web_service/testOrder_anno", map2);
        System.out.println(result2);

        Map<String, String> map3 = new HashMap<String, String>();
       /* map3.put("remark", "abcde");
        map3.put("pname", "张三丰");
        map3.put("price", "12.01");
        map3.put("productDate", new Timestamp(new Date().getTime()));*/
        map3.put("id", "211");
        map3.put("name", "我是用表单形式发送数据给你，服务器不用注解对象接收。");
        map3.put("price", "4500");
        String result3 = HttpClientUtils.httpPost_header("http://192.168.0.104:8080/web_service/testOrder_noAnno", map3);
        System.out.println(result3);

        Map<String, String> map4 = new HashMap<String, String>();
      /*  map2.put("remark", "abcde");
        map2.put("pname", "张三丰");
        map2.put("price", "12.01");
        map2.put("productDate", new Timestamp(new Date().getTime()));*/
        map4.put("id", "007");
        map4.put("name", "我是采用表单形式发送数据给你,服务器用属性接收。");
        map4.put("price", "4500");
        String httpPost_form = HttpClientUtils.httpPost_form("http://192.168.0.104:8080/web_service/test", map4);
        System.out.println(httpPost_form);
    }

}
