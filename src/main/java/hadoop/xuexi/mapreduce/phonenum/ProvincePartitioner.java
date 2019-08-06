package hadoop.xuexi.mapreduce.phonenum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import java.util.HashMap;

/**
 * Created by ASUS on 2018/5/19.
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    public static HashMap<String, Integer> provineceMap = new HashMap<>();

    static {
        provineceMap.put("133", 0);
        provineceMap.put("134", 1);
        provineceMap.put("135", 2);
        provineceMap.put("136", 3);
    }

    //这里就是实际分区方法，返回就是分区编号，分区编号就决定了数据到哪个分区中
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        Integer code = provineceMap.get(text.toString().substring(0, 3));
        if (code != null) {
            return code;
        }
        return 4;
    }
}
