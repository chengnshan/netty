package hadoop.xuexi.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ASUS on 2018/5/13.
 * 这里是mapreduce程序， mapper阶段业务逻辑实现的类
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 * KEYIN：表示数据输入时候Key的数据类型，在默认的读取数据组件下，叫InputFormat，它的行为是一行一行的读取待处理的数据
 * 读取一行，返回一行给我们的mr程序。
 * VALUEIN：表示数据输入的时候value的数据类型，在默认的读取数据组件下，valuein就表示读取的这一行内容，因此数据类型是String
 * KEYOUT：表示mapper阶段数据输出的数据Key的数据类型，在本案例当中，输出的key是单词，因此数据类型是String
 * VALUEOUT：表示mapper数据输出的时候value的数据类型，在本安全当中，输出的单词的次数，因此数据类型是Integer
 * <p>
 * hadoop自己封装一套数据类型
 * Long   --》  LongWritable
 * String --》 Text
 * Integer --》Intwritable
 * null  --》NullWritable
 */
public class WordCountmapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    /**
     * 这是mapper阶段具体的业务逻辑实现方法
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将每一行传入进来的一行内容，把数据类型转化为String
        String line = value.toString();
        //将这一行内容按照分隔符进行一行内容的切割，切割成一个单词数组
        String[] words = line.split(" ");
        for (String word : words) {
            //使用mr程序的上下文context把mapper阶段处理的数据发送出去
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
