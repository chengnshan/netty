package cxp.xuexi.LengthFieldBasedFrameDecoder.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by ASUS on 2018/3/13.
 */
public class NewClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new NewEncoder());
        pipeline.addLast(new NewClientHandler());
    }
}
