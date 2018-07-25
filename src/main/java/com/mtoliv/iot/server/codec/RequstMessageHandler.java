package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.api.TcpReader;
import com.mtoliv.iot.server.TcpServerTransportConfig;
import com.mtoliv.iot.server.api.TcpWirter;
import com.mtoliv.iot.server.message.GBT26875Message;
import com.mtoliv.iot.server.session.Session;
import com.mtoliv.iot.server.session.SessionManager;
import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.mtoliv.iot.utils.NetUtils.getChannelSessionHook;
import static com.mtoliv.iot.utils.NetUtils.getRemoteAddress;

@ChannelHandler.Sharable
public class RequstMessageHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(RequstMessageHandler.class);
    public static final AttributeKey<String> SERVER_SESSION_HOOK = AttributeKey.valueOf("ATTR_SERVER_SESSION");

    private SessionManager sessionManager ;
    private TcpReader reader = null;

    public RequstMessageHandler(TcpServerTransportConfig config) {
        this.sessionManager = config.getSessionManager() ;
        this.reader = config.getReader();
    }

    /**
     *
     * @param ctx ChannelHandlerContext
     * @param msg RequestDecoder解析好的消息
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // msg是解码之后的SocketMsg
        // SocketMsg socketMsg = (SocketMsg)msg ;
        Session session = sessionManager.getSession(ctx) ;
        Optional<Object> response = reader.readerCallback(session, msg);
        response.ifPresent(resp-> session.send(resp));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof GBT26875Message ) {
            // we forward events for our TckReader to process it
            Session session = sessionManager.getSession(ctx) ;
            Optional<Object> response = reader.readerCallback(session, evt);
            response.ifPresent(resp-> session.send(resp));
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("RequstMessageHandler (" + getRemoteAddress(ctx) + ") -> Unexpected exception from downstream." + cause);
        String sessionId0 = getChannelSessionHook(ctx);
        if (StringUtils.isNotBlank(sessionId0)) {
            logger.error("RequstMessageHandler exceptionCaught (sessionId0 -> " + sessionId0 + ", ctx -> " + ctx.toString() + ") -> Unexpected exception from downstream." + cause);
        }
    }

}
