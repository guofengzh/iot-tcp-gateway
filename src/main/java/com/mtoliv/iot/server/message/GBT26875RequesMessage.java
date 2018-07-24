package com.mtoliv.iot.server.message;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;

public class GBT26875RequesMessage implements Serializable {

	private static final long serialVersionUID = -548505853531960234L;

    private int seqNo ;
    private int version ;
    private long time ;
    private long sourceAddr ;
    private long destAddr ;
    private int dataLen ;
    private byte cmd ;
    private byte[] data ;
    private byte crc ;

	public GBT26875RequesMessage() {
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

    public byte getCrc() {
        return crc;
    }

    public void setCrc(byte crc) {
        this.crc = crc;
    }

    public static GBT26875RequesMessage fromByteBuf(ByteBuf in) {
        GBT26875RequesMessage message = new GBT26875RequesMessage() ;
      // 启动符‘@@’,(2字节)，固定值64，64
        int hd = in.readShort() ;
        int t = 64 * 256 + 64 ;
        if (hd != 64 * 256 + 64) {
            in.clear();      // 错误处理 - 因为是变长报文，后面的报文没法处理了，所以，清空
                             // 问题是，后续的报文也消除了，如果后续的报文总是不完整的，
                             // 可能会导致所有的报文都没法处理
            return null ;  // 返回错误可以抛异常，但必须在GBT26875RequestMessageDecoder.decode中
                            // 进行处理，GBT26875RequestMessageDecoder.decode中不能使用cache(Exception)
                            // 不是这里抛出的异常不在GBT26875RequestMessageDecoder.decode()中捕获，
                            // 即不捕获Runtime的异常，ReplayingDecoder需要根据这些异常知道是不是所有
                            // 报文数据都齐了
                            // 后续错误处理类似处理
        }

        // 业务流水号, (2字节)
        message.setSeqNo(in.readShort()) ;

        // 协议版本,(2字节)
        message.setVersion(in.readShort()) ;

        // 时间标签, (6字节)
        message.setTime(get6ByteLong(in)) ;

        // 源地址，(6字节)
        message.setSourceAddr(get6ByteLong(in)) ;

        // 目的地址，(6字节)
        message.setDestAddr(get6ByteLong(in)) ;

        // 应用数据单元长,(2字节)
        message.setDataLen(in.readShort()) ;

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
        int endSign = in.readShort() ;
        if (endSign != 35 * 256 + 35) {
            // 错误处理
            return null ;
        }

        // 在这里做校验和处理，保持in不变。in中如果还有内容，它是后续报文的内容

        return message ;
	}

    @Override
    public String toString() {
        return "GBT26875RequesMessage{} time:" + Long.toHexString(time);
    }

    private static long get6ByteLong(ByteBuf in) {
        long lowend = 0x0000FFFF & in.readShort() ;
        long highend = 0x00FFL & in.readByte() ;
        return (highend << 16) | lowend ;
    }
}
