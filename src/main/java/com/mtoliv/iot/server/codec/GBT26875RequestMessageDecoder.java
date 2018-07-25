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
        // 启动符‘@@’,(2字节)，固定值64，64
        int hd = in.readUnsignedShortLE() ;
        message.setStarter(hd);
        if (hd != ( 64 << 8 ) + 64 ) {
            // 这个包不对，忽略所有的字节
            int length = in.readableBytes();
            in.skipBytes(length);
            message.setStatus(GBT26875RequesMessage.MessageStatus.HEADER_MISMATCH);
            out.add(message) ;
            return ;
        }

        // 业务流水号, (2字节)
        message.setSeqNo(in.readUnsignedShortLE()) ;

        // 协议版本,(2字节)
        message.setVersion(in.readUnsignedShortLE()) ;

        // 时间标签, (6字节)
        message.setTime(get6ByteLong(in)) ;

        // 源地址，(6字节)
        message.setSourceAddr(get6ByteLong(in)) ;

        // 目的地址，(6字节)
        message.setDestAddr(get6ByteLong(in)) ;

        // 应用数据单元长,(2字节)
        message.setDataLen(in.readUnsignedShortLE()) ;
        if (message.getDataLen() > 1024 ) {
            // skip all inboundd bytes
            int length = in.readableBytes();
            in.skipBytes(length);
            message.setStatus(GBT26875RequesMessage.MessageStatus.DATA_LEN_ZERO_OR_TOO_LARGE);
            out.add(message) ;
        }

        // 命令字节, (1字节)
        message.setCmd(in.readByte()) ;

        // 应用数据单元,(最大1 024字节)
        ByteBuf dataBuf = in.readBytes(message.getDataLen()) ;
        byte[] bytes = new byte[message.getDataLen()];
        dataBuf.readBytes(bytes) ;
        message.setData(bytes) ;
        dataBuf.release() ;
        // 校验和, (1字节)
        message.setCrc(in.readByte()) ;

        // 结束符‘##，(2字节), 固定值35，35
        int endSign = in.readUnsignedShortLE() ;
        if (endSign != (35 << 8 ) + 35) {
            message.setStatus(GBT26875RequesMessage.MessageStatus.TEMINATOR_MISMATCH);
            out.add(message) ;
            return ;
        }

        // 在这里做校验和处理
        // IP包有数据包的校验和处理(确保传输中数据包不损坏)，所以，这里校验和处理没有意义，可以不做。
        out.add(message) ;
    }

    private static long get6ByteLong(ByteBuf in) {
        long lowend = in.readUnsignedIntLE() ;
        Long.toHexString(lowend) ;
        long highend = in.readUnsignedShortLE() ;
        Long.toHexString(highend) ;
        return (highend << 32) | lowend ;
    }
}