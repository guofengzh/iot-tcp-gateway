package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.GBT26875RequesMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class GBT26875RequestMessageDecoder extends ReplayingDecoder<GBT26875RequesMessage> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 低字节先传输。
        GBT26875RequesMessage message = new GBT26875RequesMessage() ;
        message.fromByteBuffer(in);
        out.add(message) ;;
    }
}