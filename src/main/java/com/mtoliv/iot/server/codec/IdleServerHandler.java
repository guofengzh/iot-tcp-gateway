package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.ReaderIdleEvent;
import com.mtoliv.iot.server.message.WriterIdleEvent;
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
        // System.err.println("---READER_IDLE---") ;
        // 发送到后续handler处理，最由TcpReader处理，
        ctx.fireUserEventTriggered(new ReaderIdleEvent());
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        // System.err.println("---WRITER_IDLE---");
        // 发送到后续handler处理，最由TcpReader处理，
        ctx.fireUserEventTriggered(new WriterIdleEvent());
    }
}