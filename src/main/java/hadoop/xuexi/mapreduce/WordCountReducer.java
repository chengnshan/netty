package hadoop.xuexi.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ASUS on 2018/5/13.
 * 这里是mr程序Reducer阶段处理的类
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    /**
     * reduce接收所有来自map阶段处理的数据之后，按照key的字典序进行排序
     *
     * 按照key是相同作为一组去调用reduce方法
     * 本方法的key就是这一组相同kv对的共同key
     * 把这一组所有的v作为一个迭代器传入我们的reduce方法
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //定义一个计数器
        int count = 0;
        //遍历一组迭代器，把每一个数据1累加起来就构成了单词的总次数
        for (IntWritable value : values) {
            count += value.get();
        }
        //把最终的结果输出
        context.write(key, new IntWritable(count));
    }
}
