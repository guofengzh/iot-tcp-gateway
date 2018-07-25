package com.mtoliv.iot.server.message.payLoad;

import com.mtoliv.iot.server.message.payLoad.upstream.FC01SystemStatus;

public class PayloadObjectFactory {
    public synchronized static PayloadObject createPayloadObject(byte typeFlag ) {
        switch (typeFlag) {
            case TypeFlag.XITONG_ZHUANGTAI:
                return new FC01SystemStatus() ;
                default:
                    throw new RuntimeException("未实现的类型：" + typeFlag) ;
        }
    }
}
