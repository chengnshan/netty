package cxp.xuexi.LengthFieldBasedFrameDecoder.client;

import cxp.xuexi.LengthFieldBasedFrameDecoder.pojo.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ASUS on 2018/3/13.
 */
public class NewClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String m = "你好啊,Netty。昂昂";
        Message msg = new Message((byte) 0xCA, m.length(), m);
        ctx.writeAndFlush(msg);
    }
}
