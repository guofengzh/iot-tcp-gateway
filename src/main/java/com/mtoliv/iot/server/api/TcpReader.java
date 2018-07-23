package com.mtoliv.iot.server.api;

import com.mtoliv.iot.server.session.Session;

public interface TcpReader {
    public Object read(Session session, Object msg) ;
}
