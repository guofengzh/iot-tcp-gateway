package com.mtoliv.iot.server.message.payLoad;

import com.mtoliv.iot.server.message.GBT26875Message;

public abstract class PayloadObject implements GBT26875Message {
    // 记录解码的正确性，总为OK - 受限于GBT26875Message的定义
    private MessageStatus status = MessageStatus.OK ;

    @Override
    public MessageStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(MessageStatus status) {
        this.status = status ;
    }
}
