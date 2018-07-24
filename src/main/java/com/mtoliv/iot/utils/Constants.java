package com.mtoliv.iot.utils;

import io.netty.util.AttributeKey;

public class Constants {
    public static final AttributeKey<String> ATTR_SERVER_SESSION = AttributeKey.valueOf("ATTR_SERVER_SESSION");

    public final static String PROPERT_channel_read_idle_seconds = "channel.read.idle.seconds" ;
    public final static String PROPERT_channel_write_idle_seconds = "channel.write.idle.seconds" ;

    public final static String PROPERT_server_listen_port = "server.listen.port" ;
    public final static String PROPERT_session_expiration_seconds = "server.session.expiration.seconds" ;
}
