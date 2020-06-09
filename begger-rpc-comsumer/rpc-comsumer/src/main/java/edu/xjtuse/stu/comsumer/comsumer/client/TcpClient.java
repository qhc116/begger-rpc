package edu.xjtuse.stu.comsumer.comsumer.client;

import com.alibaba.fastjson.JSONObject;
import edu.xjtuse.stu.comsumer.comsumer.constants.Constants;
import edu.xjtuse.stu.comsumer.zookeeper.ServerWatcher;
import edu.xjtuse.stu.comsumer.zookeeper.ZooKeeperFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

import javax.jnlp.ClipboardService;
import java.util.*;

/**
 * @author 失了秩
 * @date 2020/6/6 18:56
 * @description
 */
public class TcpClient {
    private static final Bootstrap bootstrap = new Bootstrap();
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();
    private static ChannelFuture cf = null;
    private static Set<String> serverPaths = Collections.synchronizedSet(new HashSet<String>());

    static {
        CuratorFramework zkClient = ZooKeeperFactory.create();
        String host = null;
        int port = 2444;

        CuratorWatcher watcher = new ServerWatcher();


        try {
            List<String> children = zkClient.getChildren().usingWatcher(watcher).forPath(Constants.SERVER_PATH);
            for (String child : children) {
                serverPaths.add(child.split("#")[0]);
            }
            if (serverPaths.size() > 0) {
                Iterator<String> iterator = serverPaths.iterator();
                if (iterator.hasNext()) {
                    host = iterator.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            bootstrap.group(workerGroup); // (2)
            bootstrap.channel(NioSocketChannel.class); // (3)
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(64 * 1024, Delimiters.lineDelimiter()));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new ClientHandler());
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
    public static Response send(ClientRequest msg) {

        String s = JSONObject.toJSONString(msg);
        cf.channel().writeAndFlush(s);
        cf.channel().writeAndFlush("\r\n");
        DefaultFuture df = new DefaultFuture(msg);
        return df.get();

    }

    /**
     *
     * 功能描述:监听到节点变化的时候会调用这个方法更新服务器地址
     */
    public static void updateServerPaths(List<String> paths) {
        serverPaths.clear();
        for (String path : paths) {
            String s = path.split("#")[0];
            serverPaths.add(s);
        }
    }
}
