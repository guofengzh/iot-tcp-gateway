package com.mtoliv.iot.utils;

import io.netty.util.AttributeKey;

public class Constants {
    public static final AttributeKey<String> ATTR_SERVER_SESSION = AttributeKey.valueOf("ATTR_SERVER_SESSION");
    public final static String PROPERT_server_listen_port = "server.listen.port" ;
    public final static String PROPERT_session_expiration_second = "server.session.expiration.second" ;
}
