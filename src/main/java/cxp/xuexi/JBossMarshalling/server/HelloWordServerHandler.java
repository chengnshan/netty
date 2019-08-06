package cxp.xuexi.JBossMarshalling.server;

import cxp.xuexi.JBossMarshalling.Msg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by ASUS on 2018/3/15.
 */
public class HelloWordServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            System.out.println(msg.toString());
        }else{
            ctx.writeAndFlush("received your msg");
            Msg m = (Msg)msg;
            System.out.println("client: "+m.getBody());
            m.setBody("人生苦短，快用python");
            ctx.writeAndFlush(m);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
