package com.mtoliv.iot.client.codec;

import com.mtoliv.iot.server.message.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ResponseDataDecoder extends ReplayingDecoder<ResponseMessage> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        ResponseMessage data = new ResponseMessage();
        data.setIntValue(in.readInt());
        out.add(data);
    }
}