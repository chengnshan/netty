package hadoop.xuexi.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by ASUS on 2018/5/13.
 * 这个类就是mr程序运行时候的主类，本类中组装了一些程序运行时候所需要的信息
 */
public class WordCountDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //指定本次mr job jar包运行的主类
        job.setJarByClass(WordCountDriver.class);
        //指定本次mr所用的mapper  reducer类分别是什么
        job.setMapperClass(WordCountmapper.class);
        job.setReducerClass(WordCountReducer.class);
        //指定本次mr、mapper阶段的输出  k  v 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //指定本次mr最终输出的 k  v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //指定本次mr输入  输出路径
        FileInputFormat.setInputPaths(job, "D:\\dowload\\wordcount\\input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\dowload\\wordcount\\output"));
        //提交程序 并且监控打印程序执行情况
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
