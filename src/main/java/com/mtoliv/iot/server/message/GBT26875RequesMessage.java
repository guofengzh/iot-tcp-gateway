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
            in.clear(); // 错误处理
            return null ;
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

        // 做校验，如果不对 in.clear(), 返回

        // 结束符‘##，(2字节), 固定值35，35
        int endSign = in.readShort() ;
        if (endSign != 35 * 256 + 35) {
            in.clear() ;  // 错误处理
            return null ;
        }
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
