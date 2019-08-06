package cxp.xuexi.httpclient.httpmethod;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ASUS on 2018/3/10.
 */
public class HttpGetDownloadStream {
    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    private void httpDownloadFile(String url, String filePath, Map<String, String> headMap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            setGetHead(httpGet, headMap);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                //HTTP/1.1 200
                System.out.println(response1.getStatusLine());
                HttpEntity httpEntity = response1.getEntity();
                //下载地址http://localhost:8089/download
                java.net.URI locationHeader = httpGet.getURI();
                System.out.println(locationHeader.toString());
                String downFileName = getFileName(response1);
                //获取下载的文件名称
                System.out.println(downFileName);
                InputStream is = httpEntity.getContent();
                // 根据InputStream 下载文件
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int r = 0;
                long totalRead = 0;
                while ((r = is.read(buffer)) > 0) {
                    output.write(buffer, 0, r);
                }
                FileOutputStream fos = new FileOutputStream(new File(filePath,downFileName));
                output.writeTo(fos);
                output.flush();
                output.close();
                fos.close();
                EntityUtils.consume(httpEntity);
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取response header中Content-Disposition中的filename值
     * @param response
     * @return
     */
    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        filename = new String(param.getValue().toString().getBytes("ISO-8859-1"), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        //   filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
    /**
     * 设置http的HEAD
     * @param httpGet
     * @param headMap
     */
    private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headMap.get(key));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        HttpGetDownloadStream httpGetDownloadStream = new HttpGetDownloadStream();
        Map<String, String> headMap = new HashMap<String, String>();
        headMap.put("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
        httpGetDownloadStream.httpDownloadFile("http://localhost:8012/product/downloadExcelHttpClient?username=张三丰&password=123456",
                "D:\\BaiduNetdiskDownload\\", headMap);
    }
}
