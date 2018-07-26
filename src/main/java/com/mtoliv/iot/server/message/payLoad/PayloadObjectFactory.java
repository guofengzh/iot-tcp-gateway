package com.mtoliv.iot.server.message.payLoad;

import com.mtoliv.iot.server.message.payLoad.downstream.FC90XingXiChuanFuZhuangZhiShiJian;
import com.mtoliv.iot.server.message.payLoad.upstream.FC01XiTongZhuangTai;

public class PayloadObjectFactory {
    public synchronized static PayloadObject createPayloadObject(Payload payload, byte typeFlag ) {
        PayloadObject object = null ;
        switch (typeFlag) {
            // 上行
            case PayloadObjectTypeFlag.FC01XiTongZhuangTai:
                object =  new FC01XiTongZhuangTai() ;
                break ;

            // 下行
            case PayloadObjectTypeFlag.FC90XingXiChuanFuZhuangZhiShiJiang:
                object = new FC90XingXiChuanFuZhuangZhiShiJian() ;
                break;

                default:
                    throw new RuntimeException("未实现的类型：" + typeFlag) ;
        }
        payload.setTypeFlag(typeFlag);
        return  object ;
    }
}
