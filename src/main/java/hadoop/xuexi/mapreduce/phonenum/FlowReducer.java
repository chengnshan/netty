package hadoop.xuexi.mapreduce.phonenum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by ASUS on 2018/5/19.
 */
public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
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
