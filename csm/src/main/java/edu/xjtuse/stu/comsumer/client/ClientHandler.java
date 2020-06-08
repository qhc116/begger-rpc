package edu.xjtuse.stu.comsumer.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 失了秩
 * @date 2020/6/6 16:12
 * @description
 */
public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
        Response response;
        response = JSONObject.parseObject(msg.toString(), Response.class);
        if (response == null) {
            // 放行心跳包
            ctx.fireChannelRead(msg);
        }
        DefaultFuture.receive(response);
    }
}
