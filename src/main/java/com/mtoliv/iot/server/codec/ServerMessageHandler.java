package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.api.TcpReader;
import com.mtoliv.iot.server.ServerTransportConfig;
import com.mtoliv.iot.server.session.Session;
import com.mtoliv.iot.server.session.SessionManager;
import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ServerMessageHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ServerMessageHandler.class);
    public static final AttributeKey<String> SERVER_SESSION_HOOK = AttributeKey.valueOf("SERVER_SESSION_HOOK");

    private SessionManager sessionManager ;
    private TcpReader proxy = null;

    public ServerMessageHandler(ServerTransportConfig config) {
        this.sessionManager = config.getSessionManager() ;
        this.proxy = config.getProxy();
    }

    /**
     *
     * @param ctx ChannelHandlerContext
     * @param msg RequestDecoder解析好的消息
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理一条解析好的协议 - 这里可能会有ping时所获取的设备版本号的响应消息, proxy需要处理吗?
        Session session = sessionManager.getSession(ctx) ;
        // TODO: how we do it
        // 我们将它们放到一个消息Queue中
        Object response = proxy.read(session, msg);
        if ( response != null ) {
            session.send(response);
        }
    }
}
