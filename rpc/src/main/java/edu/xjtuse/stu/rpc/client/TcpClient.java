package edu.xjtuse.stu.rpc.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author 失了秩
 * @date 2020/6/6 18:56
 * @description
 */
public class TcpClient {
    static final Bootstrap bootstrap = new Bootstrap();
    static EventLoopGroup workerGroup = new NioEventLoopGroup();
    static ChannelFuture cf = null;

    static {
        String host = "127.0.0.1";
        int port = 2444;

        try {
            bootstrap.group(workerGroup); // (2)
            bootstrap.channel(NioSocketChannel.class); // (3)
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(64 * 1024, Delimiters.lineDelimiter()));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new SimplClientHandler());
                    ch.pipeline().addLast(new StillAliveHandler());
                    ch.pipeline().addLast(new StringEncoder());
                }
            });

            cf = bootstrap.connect(host, port).sync();
        } catch (Exception e){
            e.printStackTrace();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     *  send request to edu.xjtuse.stu.rpc.client.server
     *  1. every req use same connection
     *  2. use unique id to distinguish different req
     *  3. req should take unique id
     */
    public static ClientResponse send(ClientRequest msg) {
        String s = JSONObject.toJSONString(msg);
        cf.channel().writeAndFlush(s);
        cf.channel().writeAndFlush("\r\n");
        DefaultFuture df = new DefaultFuture(msg);
        return df.get();
    }
}
