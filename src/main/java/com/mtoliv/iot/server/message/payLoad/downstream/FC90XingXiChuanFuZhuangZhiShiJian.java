package com.mtoliv.iot.server.message.payLoad.downstream;

import com.mtoliv.iot.server.message.payLoad.PayloadObject;
import io.netty.buffer.ByteBuf;

public class FC90XingXiChuanFuZhuangZhiShiJian extends PayloadObject {
    // 6字节
    private int second ;
    private int minute ;
    private int hour ;
    private int day ;
    private int month ;
    private int year ;

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
        return "FC90XingXiChuanFuZhuangZhiShiJian{" +
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
        return 6;
    }

    @Override
    public long getCrc() {
        return second + minute + hour + day + month + year;
    }

    @Override
    public void fromByteBuffer(ByteBuf in) {
        this.setSecond(in.readUnsignedByte()) ;
        this.setMinute(in.readUnsignedByte()); ;
        this.setHour(in.readUnsignedByte()); ;
        this.setDay(in.readUnsignedByte()); ;
        this.setMonth(in.readUnsignedByte()); ;
        this.setYear(in.readUnsignedByte()); ;
    }

    @Override
    public void toByteBuffer(ByteBuf out) {
        // 时间标签, (6字节)
        out.writeByte(getSecond()) ;
        out.writeByte(getMinute()) ;
        out.writeByte(getHour()) ;
        out.writeByte(getDay()) ;
        out.writeByte(getMonth()) ;
        out.writeByte(getYear()) ;
    }
}
