package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.RequestMesage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

@Deprecated
public class RequestMessageDecoder extends ReplayingDecoder<RequestMesage> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 根据协议,解析数据,放到out中
        RequestMesage msg = new RequestMesage();
        msg.setIntValue(in.readInt());
        int strLen = in.readInt();
        msg.setStringValue(in.readCharSequence(strLen, charset).toString());
        out.add(msg);
    }

    private long get6ByteLong(ByteBuf in) {
        int lowend = in.readInt() ;
        long highend = 0x00FFL & in.readByte() ;
        return (highend << 16) | lowend ;
    }
}