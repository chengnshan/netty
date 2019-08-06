package cxp.xuexi.LengthFieldBasedFrameDecoder.server;

import cxp.xuexi.LengthFieldBasedFrameDecoder.pojo.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by ASUS on 2018/3/13.
 */
public class NewServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof Message) {
            Message msg = (Message) o;
            System.out.println("Client->Server:" + channelHandlerContext.channel().remoteAddress() + " send " + msg.getMsgBody());
        }
    }
}
