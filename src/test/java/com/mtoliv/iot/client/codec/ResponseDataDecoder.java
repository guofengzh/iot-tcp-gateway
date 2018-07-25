package com.mtoliv.iot.client.codec;

import com.mtoliv.iot.server.message.GBT26875Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ResponseDataDecoder extends ReplayingDecoder<GBT26875Message> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        GBT26875Message message = new GBT26875Message() ;
        message.fromByteBuffer(in);
        out.add(message) ;
    }
}