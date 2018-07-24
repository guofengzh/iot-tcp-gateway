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
        int hd = in.readShortLE() ;
        if (hd != ( 64 << 8 ) + 64 ) {
            // 这个包不对，忽略所有的字节
            int length = in.readableBytes();
            in.skipBytes(length);

            // 错误处理

            return ;
        }

        // 业务流水号, (2字节)
        message.setSeqNo(in.readShortLE()) ;

        // 协议版本,(2字节)
        message.setVersion(in.readShortLE()) ;

        // 时间标签, (6字节)
        message.setTime(get6ByteLong(in)) ;

        // 源地址，(6字节)
        message.setSourceAddr(get6ByteLong(in)) ;

        // 目的地址，(6字节)
        message.setDestAddr(get6ByteLong(in)) ;

        // 应用数据单元长,(2字节)
        message.setDataLen(in.readShortLE()) ;

        // 命令字节, (1字节)
        message.setCmd(in.readByte()) ;

        // 应用数据单元,(最大1 024字节)
        ByteBuf dataBuf = in.readBytes(message.getDataLen()) ;
        byte[] bytes = new byte[message.getDataLen()];
        dataBuf.readBytes(bytes) ;
        message.setData(bytes) ;
        dataBuf.release() ;
        // 校验, (1字节)
        message.setCrc(in.readByte()) ;

        // 结束符‘##，(2字节), 固定值35，35
        int endSign = in.readShortLE() ;
        if (endSign != (35 << 8 ) + 35) {
            // 包的结尾不对。错误处理
        }

        // 在这里做校验和处理，保持in不变。in中如果还有内容，它是后续报文的内容
        // 如果没有错误，加入到out中。
        out.add(message) ;
    }

    private static long get6ByteLong(ByteBuf in) {
        long lowend = 0x0000FFFFL & in.readShortLE() ;
        long highend = 0x00FFL & in.readByte() ;
        return (highend << 16) | lowend ;
    }
}