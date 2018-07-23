package com.mtoliv.iot.server;

import com.mtoliv.iot.server.api.TcpReader;
import com.mtoliv.iot.server.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerTransportConfig {

    @Autowired
    private SessionManager sessionManager = null;

    @Autowired
    private TcpReader proxy = null;

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public TcpReader getProxy() {
        return proxy;
    }
}
