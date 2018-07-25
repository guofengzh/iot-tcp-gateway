package com.mtoliv.iot.server.message.payLoad;

import com.mtoliv.iot.server.message.payLoad.downstream.FC90XingXiChuanFuZhuangZhiShiJiang;
import com.mtoliv.iot.server.message.payLoad.upstream.FC01XiTongZhuangTai;

public class PayloadObjectFactory {
    public synchronized static PayloadObject createPayloadObject(byte typeFlag ) {
        switch (typeFlag) {
            // 上行
            case TypeFlag.XITONG_ZHUANGTAI:
                return new FC01XiTongZhuangTai() ;

            // 下行
            case TypeFlag.XINXI_CHUANSHU_ZHUANGZHI_SHIJIANG:
                return new FC90XingXiChuanFuZhuangZhiShiJiang() ;

                default:
                    throw new RuntimeException("未实现的类型：" + typeFlag) ;
        }
    }
}
