package hadoop.xuexi.mapreduce.phonenum;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by ASUS on 2018/5/19.
 */
public class FlowBean implements WritableComparable<FlowBean> {
    private Long upFlow;
    private Long downFlow;
    private Long sumFlow;

    public FlowBean() {
    }

    public FlowBean(Long upFlow, Long downFlow, Long sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }

    public Long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    public Long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    public Long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(Long sumFlow) {
        this.sumFlow = sumFlow;
    }

    /**
     * 如果指定的数与参数相等返回 0
     * 如果指定的数小于参数返回 -1
     * 如果指定的数大于参数返回 1
     * @param o
     * @return
     */
    @Override
    public int compareTo(FlowBean o) {
        return this.sumFlow > o.getSumFlow() ? -1 : 1;
    }

    /**
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    /**
     * 反序列化的方法，反序列化时，从流中读取的各个字段的顺序应该与序列化时写出去的顺序保持一致
     *
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readLong();
        this.downFlow = in.readLong();
        this.sumFlow = in.readLong();
    }

    @Override
    public String toString() {
        return "\t" + upFlow + "\t" + downFlow + "\t" + sumFlow + "\t";
    }
}
