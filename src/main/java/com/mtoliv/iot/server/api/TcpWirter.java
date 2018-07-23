package com.mtoliv.iot.server.api;

import com.mtoliv.iot.server.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;

public class TcpWirter {

    private final static Logger logger = LoggerFactory.getLogger(TcpWirter.class);

    public CompletionStage<Void> send(Session session, Object msg) {
        return session.send(msg);
    }
}
