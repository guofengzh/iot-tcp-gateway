package com.mtoliv.iot.client.codec;

import com.mtoliv.iot.server.message.ResponseMessage;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture future = ctx.writeAndFlush(outData);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println((ResponseMessage)msg);

        // 不急于关闭客户端连接，模拟连接空闲
        Thread.sleep(20 * 1000);

        ctx.close();
    }

    // Little-endian
    byte[] outData = {
            (byte)64,
            (byte)64,
            (byte)0xaa, // seq no
            (byte)0xbb,
            (byte)0x01, // version
            (byte)0x01,
            (byte)0xf1, // time tab
            (byte)0xf2,
            (byte)0xf3,
            (byte)0xf4,
            (byte)0xf5,
            (byte)0xf6,
            (byte)0xc1, // source addr
            (byte)0xc2,
            (byte)0xc3,
            (byte)0xc4,
            (byte)0xc5,
            (byte)0xc6,
            (byte)0xd1, // dest addr
            (byte)0xd2,
            (byte)0xd3,
            (byte)0xd4,
            (byte)0xd5,
            (byte)0xd6,
            (byte)5,       // data len
            (byte)0,
            (byte)0x01,    // cmd
            (byte)0xf1,    // data
            (byte)0xf2,
            (byte)0xf3,
            (byte)0xf4,
            (byte)0xf5,
            (byte)0xee, // crc, dummy value
            (byte)35,
            (byte)35
    } ;
}