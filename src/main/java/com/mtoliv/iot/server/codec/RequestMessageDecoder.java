package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.RequestMesage;
import com.mtoliv.socket.model.SocketMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

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

/*
        // 启动符‘@@’,(2字节)，固定值64，64
        if (in.readInt() != 64 * 100 + 64) {
            in.clear(); // 错误处理
            return ;
        }

        // 业务流水号, (2字节)
        int seqNo = in.readInt() ;

        // 协议版本,(2字节)
        int version = in.readInt() ;

        // 时间标签, (6字节)
        long time = get6ByteLong(in) ;

        // 源地址，(6字节)
        long sourceAddr = get6ByteLong(in) ;

        // 目的地址，(6字节)
        long destAddr = get6ByteLong(in) ;

        // 应用数据单元长,(2字节)
        int dataLen = in.readInt() ;

        // 命令字节, (1字节)
        byte cmd = in.readByte() ;

        // 应用数据单元,(最大1 024字节)
        ByteBuf dataBuf = in.readBytes(dataLen) ;
        byte[] data = new byte[dataBuf.readableBytes()];
        buf.readBytes(data);
        dataBuf.release() ;
        // 校验, (1字节)
        byte crc = in.readByte() ;

        // 做校验，如果不对 in.clear(), 返回

        // 结束符‘##，(2字节), 固定值35，35
        int endSign = in.readInt() ;
        if (endSign != 35 * 100 + 35) {
            in.clear() ;  // 错误处理
            return ;
        } else {
            // 将上述解析出来的数据构成一个socketMsg
            SocketMsg socketMsg = null ; //
            out.add(socketMsg) ;
        }
*/
    }

    private long get6ByteLong(ByteBuf in) {
        int lowend = in.readInt() ;
        long highend = 0x00FFL & in.readByte() ;
        return (highend << 16) | lowend ;
    }
}