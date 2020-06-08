package edu.xjtuse.stu.rpc.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 失了秩
 * @date 2020/6/6 16:12
 * @description
 */
public class SimplClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
        ClientResponse response;
        response = JSONObject.parseObject(msg.toString(), ClientResponse.class);
        if (response == null) {
            // 放行心跳包
            ctx.fireChannelRead(msg);
        }
        DefaultFuture.receive(response);
    }
}
