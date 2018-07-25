package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.GBT26875Message;
import com.mtoliv.iot.server.message.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseMessageEncoder extends MessageToByteEncoder<GBT26875Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GBT26875Message msg, ByteBuf out) throws Exception {
        // 将msg按线上协议编码,发送到线上
        msg.toByteBuffer(out);
    }
}