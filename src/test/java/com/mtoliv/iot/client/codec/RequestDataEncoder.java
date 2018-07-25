package com.mtoliv.iot.client.codec;

import com.mtoliv.iot.server.message.GBT26875Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class RequestDataEncoder extends MessageToByteEncoder<GBT26875Message> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx, GBT26875Message message, ByteBuf out) throws Exception {
        message.toByteBuffer(out);
    }
}
