package edu.xjtuse.stu.comsumer.comsumer.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 失了秩
 * @date 2020/6/6 21:08
 * @description
 */
public class StillAliveHandler extends SimpleChannelInboundHandler {

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ("ping".equals(msg.toString())) {
            System.out.println("still alive");
            ctx.writeAndFlush("pong\r\n");
        }
    }
}
