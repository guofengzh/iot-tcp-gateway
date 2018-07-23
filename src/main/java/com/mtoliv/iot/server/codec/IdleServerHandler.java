package com.mtoliv.iot.server.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdleServerHandler  extends ChannelInboundHandlerAdapter {
    protected static Logger logger = LoggerFactory.getLogger("heartbeat");
    private String TAG = "LOG: ";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                switch (e.state()) {
                    case READER_IDLE:
                        handleReaderIdle(ctx);
                        break;
                    case WRITER_IDLE:
                        handleWriterIdle(ctx);
                        break;
//                    case ALL_IDLE:
//                        handleAllIdle(ctx);
//                        break;
                }
            }
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---") ;
        // 设备在一定的时间没有发送数据
        // 解决办法:
        //    step 1: 检查session,是否过了设定的时间,如果是,则关闭ctx: ctx.close()
        //    step 2: 否则,向设备发送ping信息,例如,取设备的版本号
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
        // 我们没有写,可能不需要处理
    }
}