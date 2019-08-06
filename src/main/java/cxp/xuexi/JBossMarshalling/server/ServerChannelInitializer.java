package cxp.xuexi.JBossMarshalling.server;

import cxp.xuexi.JBossMarshalling.MarshallingCodeCFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by ASUS on 2018/3/15.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloWordServerHandler());
    }
}
