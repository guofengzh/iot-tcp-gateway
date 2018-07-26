package com.mtoliv.iot.server.message.payLoad;

import com.mtoliv.iot.server.message.GBT26875MessageIntef;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 图5, P5结构的定义
 */
public class Payload implements GBT26875MessageIntef {
    // 解码状态 - 总为OK
    private MessageStatus status = MessageStatus.OK ;

    @Override
    public MessageStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(MessageStatus status) {
        this.status = status ;
    }

    // 李俊铭: 只做了类型为1、2、4、21、24、28这6种上行数据类型
     //        下行里也只实现了90、91两种
    //          未来还会实现类型5（上行）、85（下行），用于设备软件版本的查询。

    // 类型标志 - 1字节
    private byte typeFlag ;

    // 信息对象数目 - 1字节
    public byte getNumberOfObjects() {
        return (byte)payloadObjects.size() ;
    }

    // 信息对象1 - n, 结构：{信息体i(根据类型不周长度不同)， 时闻标簦i(6字节)}. i = 1...n
    private List<PayloadObject> payloadObjects ;

    public Payload() {
        payloadObjects = new ArrayList<>() ;
    }

    public byte getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(byte typeFlag) {
        this.typeFlag = typeFlag;
    }

    public void addPayloadObject(PayloadObject payloadObject) {
        payloadObjects.add(payloadObject) ;
    }

    public List<PayloadObject> getPayloadObjects() {
        return payloadObjects;
    }

    public void setPayloadObjects(List<PayloadObject> payloadObjects) {
        this.payloadObjects = payloadObjects;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "status=" + status +
                ", typeFlag=" + Integer.toHexString(typeFlag) +
                ", nuber =" + Integer.toHexString(payloadObjects.size()) +
                ", payloadObjects=" + payloadObjects +
                '}';
    }

    // --- 协议代码

    @Override
    public int getDataLengthInBytes() {
        int payloadObjectLen =  payloadObjects.stream().mapToInt(i->i.getDataLengthInBytes()).sum() ;
        return 1 + 1 + payloadObjectLen;
    }

    @Override
    public long getCrc() {
        long checksum = payloadObjects.stream().mapToLong(i->i.getCrc()).sum() ;
        return typeFlag + payloadObjects.size() + checksum ;
    }

    @Override
    public void fromByteBuffer(ByteBuf in) {
           this.setTypeFlag(in.readByte());
           int no = in.readByte() ;
           for (int i = 0 ; i < no ; i++ ) {
               PayloadObject payloadObject = PayloadObjectFactory.createPayloadObject(this, getTypeFlag()) ;
               payloadObject.fromByteBuffer(in) ;
               this.addPayloadObject(payloadObject);
        }
    }

    @Override
    public void toByteBuffer(ByteBuf out) {
          out.writeByte(typeFlag) ;
          out.writeByte(getNumberOfObjects()) ;
          for (PayloadObject payloadObject : payloadObjects ) {
              payloadObject.toByteBuffer(out);
          }
    }
}
