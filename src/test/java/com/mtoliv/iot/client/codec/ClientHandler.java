package com.mtoliv.iot.client.codec;

import com.mtoliv.iot.server.message.RequestMesage;
import com.mtoliv.iot.server.message.ResponseMessage;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {

        RequestMesage msg = new RequestMesage();
        msg.setIntValue(123);
        msg.setStringValue(
                "all work and no play makes jack a dull boy");
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println((ResponseMessage)msg);
        ctx.close();
    }
}