package hadoop.xuexi.mapreduce.phonenum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by ASUS on 2018/5/19.
 */
public class FlowSumDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //指定本次mr job jar包运行的主类
        job.setJarByClass(FlowSumDriver.class);
        //指定本次mr所用的mapper  reducer类分别是什么
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        //指定本次mr、mapper阶段的输出  k  v 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //指定本次mr最终输出的 k  v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //指定本次mr输入  输出路径
        FileInputFormat.setInputPaths(job, "D:\\dowload\\wordcount\\phone_input");
        FileOutputFormat.setOutputPath(job, new Path("D:\\dowload\\wordcount\\phone_output"));
        //这里设置运行reduceTask的个数
        //分区个数 == reducetask个数    正常执行
        //分区个数  大于 reducetask个数    正常执行   只不过会有空的结果文件产生
        //分区个数  小于 reducetask个数    错误       Illegal Partition
   //     job.setNumReduceTasks(5);
        //设置自定义分区组件
   //     job.setPartitionerClass(ProvincePartitioner.class);
        //提交程序 并且监控打印程序执行情况
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
