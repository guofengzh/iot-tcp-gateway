package com.mtoliv.iot.client.codec;

import com.mtoliv.iot.server.message.GBT26875Message;
import com.mtoliv.iot.server.message.ResponseMessage;
import com.mtoliv.iot.server.message.payLoad.Payload;
import com.mtoliv.iot.server.message.payLoad.PayloadObject;
import com.mtoliv.iot.server.message.payLoad.PayloadObjectTypeFlag;
import com.mtoliv.iot.server.message.payLoad.upstream.FC01XiTongZhuangTai;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*
    private int seqNo ;
    private int version ;
    private long time ;
    private long sourceAddr ;
    private long destAddr ;
    //private int dataLen ;
    private byte cmd ;
    private Payload data ;         */
        GBT26875Message message = new GBT26875Message() ;
        message.setSeqNo(1);
        message.setMajor(1);

        // payload
        Payload payload = new Payload() ;
        payload.setTypeFlag(PayloadObjectTypeFlag.XITONG_ZHUANGTAI);
        FC01XiTongZhuangTai payloadObject = new FC01XiTongZhuangTai() ;
        payloadObject.setSystemStatus(1);
        payload.addPayloadObject(payloadObject);
        message.setData(payload);

        ChannelFuture future = ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);

        // 不急于关闭客户端连接，模拟连接空闲
        Thread.sleep(20 * 1000);

        ctx.close();
    }
}