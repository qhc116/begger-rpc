package edu.xjtuse.stu.rpc.server;

import edu.xjtuse.stu.rpc.constants.Constants;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import edu.xjtuse.stu.rpc.zookeeper.ZooKeeperFactory;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;


/**
 * @author 失了秩
 * @date 2020/6/6 15:53
 * @description
 */
public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(parentGroup, childGroup);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128) // EventLoop 管理的最大连接数
                    // 关闭心跳
                    .childOption(ChannelOption.SO_KEEPALIVE, false)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 基于分隔符 "\r\n" 的解码器，处理粘包
                            pipeline.addLast(new DelimiterBasedFrameDecoder(64 * 1024, Delimiters.lineDelimiter()));
                            pipeline.addLast(new IdleStateHandler(60, 45, 10, TimeUnit.SECONDS));
                            // 将二进制转为字符串
                            pipeline.addLast(new StringDecoder());
                            // 在这个 Handler 中处理业务逻辑
                            pipeline.addLast(new ServerInboundHandler());
                            pipeline.addLast(new StringEncoder());
                        }
                    });
            CuratorFramework zkClient = ZooKeeperFactory.create();
            zkClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(Constants.SERVER_PATH + InetAddress.getLocalHost().getHostAddress() + '#');

            ChannelFuture cf = bootstrap.bind(2333).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            parentGroup.shutdownGracefully();
        }
    }
}
