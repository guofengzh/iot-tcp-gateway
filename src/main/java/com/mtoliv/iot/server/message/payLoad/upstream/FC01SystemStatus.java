package com.mtoliv.iot.server.message.payLoad.upstream;

import com.mtoliv.iot.server.message.payLoad.PayloadObject;
import io.netty.buffer.ByteBuf;

/**
 * 8.3.1.1 上传建筑消防设施系统状态, 图18，P17
 */
public class FC01SystemStatus extends PayloadObject {
    private byte systemType ;     // 系统类型1(1字节)
    private byte systemAddress ;  // 系统地址1(1字节)
    private int systemStatus ;    // 系统状舂1(2字节)
    private long timeHappened ;   // 状态发生时间(6字节)

    public byte getSystemType() {
        return systemType;
    }

    public void setSystemType(byte systemType) {
        this.systemType = systemType;
    }

    public byte getSystemAddress() {
        return systemAddress;
    }

    public void setSystemAddress(byte systemAddress) {
        this.systemAddress = systemAddress;
    }

    public int getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(int systemStatus) {
        this.systemStatus = systemStatus;
    }

    public long getTimeHappened() {
        return timeHappened;
    }

    public void setTimeHappened(long timeHappened) {
        this.timeHappened = timeHappened;
    }

    @Override
    public String toString() {
        return "FC01SystemStatus{" +
                "systemType=" + Integer.toHexString(systemType) +
                ", systemAddress=" + Integer.toHexString(systemAddress) +
                ", systemStatus=" + Integer.toHexString(systemStatus) +
                ", timeHappened=" + Long.toHexString(timeHappened) +
                '}';
    }

    // --- 协议代码

    @Override
    public long getCrc() {
        // 注，ox等操作可以不用，因为所有的字段的最高位都是0
        return systemType & 0x00FF + systemAddress & 0x00FF + systemStatus & 0x0000FFFFL + timeHappened ;
    }

    @Override
    public void fromByteBuffer(ByteBuf in) {
        this.setSystemType(in.readByte());
        this.setSystemAddress(in.readByte());
        this.setSystemStatus(in.readUnsignedShortLE());
        this.setTimeHappened(get6ByteLong(in));
    }

    @Override
    public void toByteBuffer(ByteBuf out) {
        out.writeByte(getSystemType()) ;
        out.writeByte(getSystemAddress()) ;
        out.writeShortLE(getSystemStatus()) ;
        setLong6Byte(out, getTimeHappened());
    }
}
