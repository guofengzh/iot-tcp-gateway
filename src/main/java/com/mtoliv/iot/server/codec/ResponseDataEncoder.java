package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        // 将msg按线上协议编码,发送到线上
        out.writeInt(msg.getIntValue());
    }
}