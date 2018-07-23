package com.mtoliv.iot.server;

import com.mtoliv.iot.server.api.TcpReader;
import com.mtoliv.iot.server.api.TcpWirter;
import com.mtoliv.iot.server.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TcpServerTransportConfig {

    @Autowired
    private SessionManager sessionManager = null;

    @Autowired
    private TcpReader reader = null;

    @Autowired
    TcpWirter wirter;

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public TcpReader getReader() {
        return reader;
    }

    public TcpWirter getWirter() {
        return wirter;
    }
}
