package edu.xjtuse.stu.comsumer.comsumer.client;

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
        Response response = null;
        if (msg.toString().equals("ping")) {
            // 放行心跳包
            ctx.fireChannelRead(msg);
        } else {
            response = JSONObject.parseObject(msg.toString(), Response.class);
        }
        if (response != null && response.getId() != 0) {
            DefaultFuture.receive(response);
        }
    }
}
