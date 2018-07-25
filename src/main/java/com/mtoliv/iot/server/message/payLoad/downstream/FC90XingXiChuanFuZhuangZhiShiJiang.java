package com.mtoliv.iot.server.message.payLoad.downstream;

import com.mtoliv.iot.server.message.payLoad.PayloadObject;
import io.netty.buffer.ByteBuf;

public class FC90XingXiChuanFuZhuangZhiShiJiang extends PayloadObject {
    private long systemTime ; // 6字节

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    @Override
    public String toString() {
        return "FC90XingXiChuanFuZhuangZhiShiJiang{" +
                "systemTime=" + Long.toHexString(systemTime) +
                '}';
    }
// --- 协议代码

    @Override
    public int getDataLengthInBytes() {
        return 6;
    }

    @Override
    public long getCrc() {
        return systemTime;
    }

    @Override
    public void fromByteBuffer(ByteBuf in) {
        this.setSystemTime(get6ByteLong(in));

    }

    @Override
    public void toByteBuffer(ByteBuf out) {
       setLong6Byte(out, getSystemTime());
    }
}
