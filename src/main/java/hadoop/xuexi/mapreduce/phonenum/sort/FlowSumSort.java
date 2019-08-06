package hadoop.xuexi.mapreduce.phonenum.sort;

import hadoop.xuexi.mapreduce.phonenum.FlowBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by ASUS on 2018/5/19.
 */
public class FlowSumSort {
    public static class FlowSortMapper extends Mapper<LongWritable, Text, FlowBean, Text> {
        Text k = new Text();
        FlowBean flowBean = new FlowBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t+");
            String phoneNum = fields[0];

            Long upFlow = Long.parseLong(fields[1]);
            Long downFlow = Long.parseLong(fields[2]);
            k.set(phoneNum);
            flowBean.setUpFlow(upFlow);
            flowBean.setDownFlow(downFlow);
            flowBean.setSumFlow(upFlow + downFlow);
            context.write(flowBean, k);
        }
    }

    static class FlowSortReducer extends Reducer<FlowBean, Text, Text, FlowBean> {
        @Override
        protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            context.write(values.iterator().next(), key);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //指定本次mr job jar包运行的主类
        job.setJarByClass(FlowSumSort.class);
        //指定本次mr所用的mapper  reducer类分别是什么
        job.setMapperClass(FlowSortMapper.class);
        job.setReducerClass(FlowSortReducer.class);
        //指定本次mr、mapper阶段的输出  k  v 类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        //指定本次mr最终输出的 k  v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //指定本次mr输入  输出路径
        FileInputFormat.setInputPaths(job, "D:\\dowload\\wordcount\\sort_input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\dowload\\wordcount\\sort_output"));

        //提交程序 并且监控打印程序执行情况
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
