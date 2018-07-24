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
        ctx.close();
    }

    byte[] outData = {
            (byte)64,
            (byte)64,
            (byte)0xaa, // seq no
            (byte)0xbb,
            (byte)0x01, // version
            (byte)0x01,
            (byte)0xff, // time tab
            (byte)0xff,
            (byte)0xff,
            (byte)0xcc, // source addr
            (byte)0xcc,
            (byte)0xcc,
            (byte)0xdd, // dest addr
            (byte)0xdd,
            (byte)0xdd,
            (byte)0,       // data len
            (byte)5,
            (byte)0x01,    // cmd
            (byte)0xf1,    // data
            (byte)0xf2,
            (byte)0xf3,
            (byte)0xf4,
            (byte)0xf5,
            (byte)0xee, // crc, dummy value
            (byte)(byte)35,
            (byte)(byte)35
    } ;
}