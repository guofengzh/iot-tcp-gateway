package com.mtoliv.iot.server;

import com.mtoliv.iot.server.codec.RequestMessageDecoder;
import com.mtoliv.iot.server.codec.ResponseMessageEncoder;
import com.mtoliv.iot.server.codec.SessionStateHandler;
import com.mtoliv.iot.server.codec.RequstMessageHandler;
import com.mtoliv.iot.server.codec.IdleServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 *  ChannelPipeline p = ...;
 *  p.addLast("1", new InboundHandlerA());
 *  p.addLast("2", new InboundHandlerB());
 *  p.addLast("3", new OutboundHandlerA());
 *  p.addLast("4", new OutboundHandlerB());
 *  p.addLast("5", new InboundOutboundHandlerX());
 *
 *  In the given example configuration, the handler evaluation order is 1, 2, 3, 4, 5 when an event goes inbound.
 *  When an event goes outbound, the order is 5, 4, 3, 2, 1.
 *
 *  On top of this principle, ChannelPipeline skips the evaluation of certain handlers to shorten the stack depth:
 *     1) 3 and 4 don't implement ChannelInboundHandler, and therefore the actual evaluation order of an inbound event
 *        will be: 1, 2, and 5.
 *     2) 1 and 2 don't implement ChannelOutboundHandler, and therefore the actual evaluation order of a outbound
 *        event will be: 5, 4, and 3.
 *     3) If 5 implements both ChannelInboundHandler and ChannelOutboundHandler, the evaluation order of an inbound and
 *        a outbound event could be 125 and 543 respectively.
 *
 *     https://netty.io/4.0/api/io/netty/channel/ChannelPipeline.html
 */
@ChannelHandler.Sharable
public class TcpServerCodecInitializer extends ChannelInitializer<SocketChannel> {

    private TcpServerTransportConfig config;

    public TcpServerCodecInitializer(TcpServerTransportConfig config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // IdleStateHandler should always be the first handler in your pipeline.
        pipeline.addLast("idleStateHandler", new IdleStateHandler(6, 3, 0, TimeUnit.SECONDS));
        pipeline.addLast("requestDecoder", new RequestMessageDecoder());
        pipeline.addLast("responseDecoder", new ResponseMessageEncoder());
        pipeline.addLast("idleServerHandler", new IdleServerHandler());
        pipeline.addLast("sessionActivationHandler", new SessionStateHandler(config));
        pipeline.addLast("messageHandler", new RequstMessageHandler(config));
    }
}
