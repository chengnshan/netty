package cxp.xuexi.elasticsearch.client6x;

import org.elasticsearch.common.xcontent.XContentBuilder;
import java.io.IOException;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
/**
 * Created by ASUS on 2018/4/25.
 */
public class TestWSUtil {
    public static void main(String[] args) {
        //1.判定索引是否存在
        boolean flag = ESUtil.isExists("index1");
        System.out.println("isExists:" + flag);
        //2.创建索引
        flag = ESUtil.createIndex("index1", 3, 0);
        System.out.println("createIndex:" + flag);
        //3.设置Mapping
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("id")
                    .field("type", "long")
                    .endObject()
                    .startObject("title")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("search_analyzer", "ik_max_word")
                    .field("boost", 2)
                    .endObject()
                    .startObject("content")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("search_analyzer", "ik_max_word")
                    .endObject()
                    .startObject("postdate")
                    .field("type", "date")
                    .field("format", "yyyy-MM-dd HH:mm:ss")
                    .endObject()
                    .startObject("url")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject();
            System.out.println(builder.string());
            ESUtil.setMapping("index1", "blog", builder.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
