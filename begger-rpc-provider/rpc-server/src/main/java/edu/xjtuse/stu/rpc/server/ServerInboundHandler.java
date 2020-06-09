package edu.xjtuse.stu.rpc.server;

import com.alibaba.fastjson.JSONObject;
import edu.xjtuse.stu.rpc.proxy.Proxy;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * @author 失了秩
 * @date 2020/6/6 16:12
 * @description
 */
public class ServerInboundHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ServerRequest request = JSONObject.parseObject(msg.toString(), ServerRequest.class);
        if (request == null || request.getId() == 0) {
            ctx.fireChannelRead(msg);
        }
        Response response = Proxy.execute(request);
        response.setId(request.getId());

        ctx.channel().writeAndFlush(JSONObject.toJSONString(response) + "\r\n");
        ReferenceCountUtil.release(msg);
    }

    // keep alive per 10s
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();
            if (state.equals(IdleStateEvent.ALL_IDLE_STATE_EVENT.state())) {
                System.out.println("10s读写空闲，发送心跳包");
                ctx.channel().writeAndFlush("ping\r\n")
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);

            }
        }
    }
}
