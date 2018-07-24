package com.mtoliv.iot.server.message;

import io.netty.buffer.ByteBuf;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;
import java.nio.ByteOrder;

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

    @Override
    public String toString() {
        return "GBT26875RequesMessage:" +
                " seqNo:" + Long.toHexString(seqNo) +
                " version:" + Long.toHexString(version) +
                " time:" + Long.toHexString(time) +
                " sourceAddr:" + Long.toHexString(sourceAddr) +
                " destAddr:" + Long.toHexString(destAddr) +
                " dataLen:" + Long.toHexString(dataLen) +
                " cmd:" + Long.toHexString(cmd) +
                " data:" + Hex.encodeHexString( data ) +
                " crc: " + Long.toHexString(crc);
    }
}
