package com.mtoliv.iot.server.message.payLoad;

import com.mtoliv.iot.server.message.payLoad.downstream.FC90XingXiChuanFuZhuangZhiShiJiang;
import com.mtoliv.iot.server.message.payLoad.upstream.FC01XiTongZhuangTai;

public class PayloadObjectFactory {
    public synchronized static PayloadObject createPayloadObject(byte typeFlag ) {
        switch (typeFlag) {
            // 上行
            case PayloadObjectTypeFlag.FC01XiTongZhuangTai:
                return new FC01XiTongZhuangTai() ;

            // 下行
            case PayloadObjectTypeFlag.FC90XingXiChuanFuZhuangZhiShiJiang:
                return new FC90XingXiChuanFuZhuangZhiShiJiang() ;

                default:
                    throw new RuntimeException("未实现的类型：" + typeFlag) ;
        }
    }
}