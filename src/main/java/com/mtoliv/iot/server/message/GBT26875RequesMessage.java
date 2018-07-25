package com.mtoliv.iot.server.message;

import io.netty.buffer.ByteBuf;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;
import java.nio.ByteOrder;

public class GBT26875RequesMessage implements GBT26875Message, Serializable {
    private static final long serialVersionUID = -548505853531960234L;

    public static enum MessageStatus {
        OK,
        HEADER_MISMATCH,
        DATA_LEN_TOO_LARGE,
        TEMINATOR_MISMATCH
    }

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

    public MessageStatus getStatus() {
        return status;
    }

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

    public byte getCrc() {
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
}
