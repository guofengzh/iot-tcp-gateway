package com.mtoliv.iot.server.message;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.util.List;

public interface GBT26875Message extends Serializable {
    int STARTER = ( 64 << 8 ) + 64 ;
    int TERMINATOR = (35 << 8 ) + 35 ;

    enum MessageStatus {
        OK,
        HEADER_MISMATCH,
        DATA_LEN_TOO_LARGE,
        TEMINATOR_MISMATCH
    }

    default long get6ByteLong(ByteBuf in) {
        long lowend = in.readUnsignedIntLE() ;
        Long.toHexString(lowend) ;
        long highend = in.readUnsignedShortLE() ;
        Long.toHexString(highend) ;
        return (highend << 32) | lowend ;
    }

    default void setLong6Byte(ByteBuf out, long value) {
        out.writeIntLE((int)value) ;
        byte highend = (byte)(value >> 32) ;
        out.writeByte(highend) ;
    }

    /** 这个操作对Payload或PayloadObject没有用 - 再处理 */
    MessageStatus getStatus() ;
    void setStatus(MessageStatus status) ;

    /**
     * 计算校验和 - 将所有的数据按长整数相加
     *
     * @return
     */
    long getCrc() ;
    void fromByteBuffer(ByteBuf in) ;
    void toByteBuffer(ByteBuf out) ;
}
