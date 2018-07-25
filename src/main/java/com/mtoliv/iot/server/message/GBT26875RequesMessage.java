package com.mtoliv.iot.server.message;

import io.netty.buffer.ByteBuf;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.List;

public class GBT26875RequesMessage implements GBT26875Message, Serializable {
    private static final long serialVersionUID = -548505853531960234L;

    private MessageStatus status = MessageStatus.OK;

    private int starter ;
    private int seqNo ;
    private int version ;
    private long time ;
    private long sourceAddr ;
    private long destAddr ;
    private int dataLen ;
    private byte cmd ;
    private byte[] data ;
    private byte crc ;
    private int terminator ;

    public GBT26875RequesMessage() {
	}

	@Override
    public MessageStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public int getStarter() {
        return starter;
    }

    public void setStarter(int starter) {
        this.starter = starter;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(long sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public long getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(long destAddr) {
        this.destAddr = destAddr;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getCrc() {
        return crc;
    }

    public void setCrc(byte crc) {
        this.crc = crc;
    }

    public int getTerminator() {
        return terminator;
    }

    public void setTerminator(int terminator) {
        this.terminator = terminator;
    }

    @Override
    public String toString() {
        return "GBT26875RequesMessage:" +
                " status:" + status +
                " starter:" + Integer.toHexString(starter) +
                " seqNo:" + Integer.toHexString(seqNo) +
                " version:" + Integer.toHexString(version) +
                " time:" + Long.toHexString(time) +
                " sourceAddr:" + Long.toHexString(sourceAddr) +
                " destAddr:" + Long.toHexString(destAddr) +
                " dataLen:" + Integer.toHexString(dataLen) +
                " cmd:" + Integer.toHexString(cmd) +
                " data:" + Hex.encodeHexString( data ) +
                " crc: " + Hex.encodeHexString(new byte[]{crc}) +
                " terminator:" + Integer.toHexString( terminator ) ;
    }

    @Override
    public void fromByteBuffer(ByteBuf in) {
        // 启动符‘@@’,(2字节)，固定值64，64
        int hd = in.readUnsignedShortLE() ;
        this.setStarter(hd);
        if (hd != ( 64 << 8 ) + 64 ) {
            // 这个包不对，忽略所有的字节
            int length = in.readableBytes();
            in.skipBytes(length);
            this.setStatus(GBT26875RequesMessage.MessageStatus.HEADER_MISMATCH);
            return ;
        }

        // 业务流水号, (2字节)
        this.setSeqNo(in.readUnsignedShortLE()) ;

        // 协议版本,(2字节)
        this.setVersion(in.readUnsignedShortLE()) ;

        // 时间标签, (6字节)
        this.setTime(get6ByteLong(in)) ;

        // 源地址，(6字节)
        this.setSourceAddr(get6ByteLong(in)) ;

        // 目的地址，(6字节)
        this.setDestAddr(get6ByteLong(in)) ;

        // 应用数据单元长,(2字节)
        this.setDataLen(in.readUnsignedShortLE()) ;
        if (this.getDataLen() > 1024) {
            // skip all inboundd bytes
            int length = in.readableBytes();
            in.skipBytes(length);
            this.setStatus(GBT26875RequesMessage.MessageStatus.DATA_LEN_TOO_LARGE);
            return ;
        }

        // 命令字节, (1字节) 见表2，P5
        this.setCmd(in.readByte()) ;

        // 应用数据单元,(最大1 024字节)
        ByteBuf dataBuf = in.readBytes(this.getDataLen()) ;
        byte[] bytes = new byte[this.getDataLen()];
        dataBuf.readBytes(bytes) ;
        this.setData(bytes) ;
        dataBuf.release() ;
        // 校验和, (1字节)
        this.setCrc(in.readByte()) ;

        // 结束符‘##，(2字节), 固定值35，35
        int endSign = in.readUnsignedShortLE() ;
        if (endSign != (35 << 8 ) + 35) {
            this.setStatus(GBT26875RequesMessage.MessageStatus.TEMINATOR_MISMATCH);
            return ;
        }

        // 在这里做校验和处理
        // IP包有数据包的校验和处理(确保传输中数据包不损坏)，所以，这里校验和处理没有意义，可以不做。
    }

    @Override
    public void toByteBuffer(ByteBuf out) {
        // 启动符‘@@’,(2字节)，固定值64，64
        out.writeShortLE(STARTER) ;

        // 业务流水号, (2字节)
        out.writeShortLE(getSeqNo()) ;

        // 协议版本,(2字节)
        out.writeShortLE(getVersion()) ;

        // 时间标签, (6字节)
        setLong6Byte(out, getTime());

        // 源地址，(6字节)
        setLong6Byte(out, getSourceAddr());

        // 目的地址，(6字节)
        setLong6Byte(out, getDestAddr());

/*   TODO: replace with payload
        // 应用数据单元长,(2字节)
        this.setDataLen(in.readUnsignedShortLE()) ;
        if (this.getDataLen() > 1024) {
            // skip all inboundd bytes
            int length = in.readableBytes();
            in.skipBytes(length);
            this.setStatus(GBT26875RequesMessage.MessageStatus.DATA_LEN_TOO_LARGE);
            return ;
        }

        // 命令字节, (1字节)
        this.setCmd(in.readByte()) ;

        // 应用数据单元,(最大1 024字节)
        ByteBuf dataBuf = in.readBytes(this.getDataLen()) ;
        byte[] bytes = new byte[this.getDataLen()];
        dataBuf.readBytes(bytes) ;
        this.setData(bytes) ;
        dataBuf.release() ;
        // 校验和, (1字节)
        this.setCrc(in.readByte()) ;
*/
        // 结束符‘##，(2字节), 固定值35，35
        out.writeIntLE(TERMINATOR) ;
    }
}
