package hadoop.xuexi.mapreduce.phonenum.partition;

import hadoop.xuexi.mapreduce.phonenum.FlowBean;
import hadoop.xuexi.mapreduce.phonenum.ProvincePartitioner;
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
public class FlowPartition {
    public static class FlowParMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
        Text k = new Text();
        FlowBean flowBean = new FlowBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t+");
            String phoneNum = fields[1];

            Long upFlow = Long.parseLong(fields[fields.length - 3]);
            Long downFlow = Long.parseLong(fields[fields.length - 2]);
            k.set(phoneNum);
            flowBean.setUpFlow(upFlow);
            flowBean.setDownFlow(downFlow);
            flowBean.setSumFlow(upFlow + downFlow);
            context.write(k, flowBean);
        }
    }

    public static class FlowParReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
        FlowBean flowBean = new FlowBean();

        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            long upFlowCount = 0;
            long downFlowCount = 0;
            for (FlowBean f : values) {
                upFlowCount += f.getUpFlow();
                downFlowCount += f.getDownFlow();
            }
            flowBean.setUpFlow(upFlowCount);
            flowBean.setDownFlow(downFlowCount);
            flowBean.setSumFlow(upFlowCount + downFlowCount);
            context.write(key, flowBean);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //指定本次mr job jar包运行的主类
        job.setJarByClass(FlowPartition.class);
        //指定本次mr所用的mapper  reducer类分别是什么
        job.setMapperClass(FlowParMapper.class);
        job.setReducerClass(FlowParReducer.class);
        //指定本次mr、mapper阶段的输出  k  v 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //指定本次mr最终输出的 k  v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //指定本次mr输入  输出路径
        FileInputFormat.setInputPaths(job, "D:\\dowload\\wordcount\\partition_input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\dowload\\wordcount\\partition_output"));

        //这里设置运行reduceTask的个数
        //分区个数 == reducetask个数    正常执行
        //分区个数  大于 reducetask个数    正常执行   只不过会有空的结果文件产生
        //分区个数  小于 reducetask个数    错误       Illegal Partition
        job.setNumReduceTasks(5);
        //设置自定义分区组件
        job.setPartitionerClass(ProvincePartitioner.class);
        //提交程序 并且监控打印程序执行情况
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
