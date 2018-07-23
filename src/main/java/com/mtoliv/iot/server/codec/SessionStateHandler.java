package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.TcpServerTransportConfig;
import com.mtoliv.iot.server.session.Session;
import com.mtoliv.iot.server.session.SessionManager;
import com.mtoliv.iot.utils.NetUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SessionStateHandler extends ChannelInboundHandlerAdapter {

    private SessionManager sessionManager ;

    public SessionStateHandler(TcpServerTransportConfig config) {
        this.sessionManager = config.getSessionManager() ;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Session session = sessionManager.createSession(ctx) ;
        NetUtils.setChannelSessionHook(ctx, session.getSessionId());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        sessionManager.removeSession(ctx);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session = sessionManager.getSession(ctx) ;
        session.setActive(true);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = sessionManager.getSession(ctx) ;
        session.setActive(false);
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        sessionManager.updateSession(ctx);
        super.channelRead(ctx, msg);
    }
}
