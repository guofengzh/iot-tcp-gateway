package com.mtoliv.iot.server.message.payLoad.upstream;

import com.mtoliv.iot.server.message.payLoad.PayloadObject;
import io.netty.buffer.ByteBuf;

/**
 * 8.3.1.1 上传建筑消防设施系统状态, 图18，P17
 */
public class FC01XiTongZhuangTai extends PayloadObject {
    private byte systemType ;     // 系统类型1(1字节)
    private byte systemAddress ;  // 系统地址1(1字节)
    private int systemStatus ;    // 系统状舂1(2字节)
    // 状态发生时间(6字节)
    private int second ;
    private int minute ;
    private int hour ;
    private int day ;
    private int month ;
    private int year ;

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

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "FC01XiTongZhuangTai{" +
                "systemType=" + Integer.toHexString(systemType) +
                ", systemAddress=" + Integer.toHexString(systemAddress) +
                ", systemStatus=" + Integer.toHexString(systemStatus) +
                " second:" + Integer.toHexString(second) +
                " minute:" + Integer.toHexString(minute) +
                " hour:" + Integer.toHexString(hour) +
                " day:" + Integer.toHexString(day) +
                " month:" + Integer.toHexString(month) +
                " year:" + Integer.toHexString(year) +
                '}';
    }

    // --- 协议代码

    @Override
    public int getDataLengthInBytes() {
        return 1 + 1 + 2 + 6 ;
    }

    @Override
    public long getCrc() {
        // 注，ox等操作可以不用，因为所有的字段的最高位都是0
        return systemType & 0x00FF + systemAddress & 0x00FF + systemStatus & 0x0000FFFFL +
                second + minute + hour + day + month + year ;
    }

    @Override
    public void fromByteBuffer(ByteBuf in) {
        this.setSystemType(in.readByte());
        this.setSystemAddress(in.readByte());
        this.setSystemStatus(in.readUnsignedShortLE());
        this.setSecond(in.readUnsignedByte()) ;
        this.setMinute(in.readUnsignedByte()); ;
        this.setHour(in.readUnsignedByte()); ;
        this.setDay(in.readUnsignedByte()); ;
        this.setMonth(in.readUnsignedByte()); ;
        this.setYear(in.readUnsignedByte()); ;
    }

    @Override
    public void toByteBuffer(ByteBuf out) {
        out.writeByte(getSystemType()) ;
        out.writeByte(getSystemAddress()) ;
        out.writeShortLE(getSystemStatus()) ;
        out.writeByte(getSecond()) ;
        out.writeByte(getMinute()) ;
        out.writeByte(getHour()) ;
        out.writeByte(getDay()) ;
        out.writeByte(getMonth()) ;
        out.writeByte(getYear()) ;
    }
}
