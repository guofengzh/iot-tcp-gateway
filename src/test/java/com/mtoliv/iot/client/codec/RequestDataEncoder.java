package com.mtoliv.iot.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class RequestDataEncoder extends MessageToByteEncoder<byte[]> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] bytes, ByteBuf out) throws Exception {
//        out.writeInt(msg.getIntValue());
//        out.writeInt(msg.getStringValue().length());
//        out.writeCharSequence(msg.getStringValue(), charset);
        out.writeBytes(bytes) ;
    }
}
