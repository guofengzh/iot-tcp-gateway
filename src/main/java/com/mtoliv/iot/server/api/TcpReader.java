package com.mtoliv.iot.server.api;

import com.mtoliv.iot.server.session.Session;

import java.util.Optional;

public interface TcpReader {
    /**
     *  消息已被解码，调用此方法
     *
     * @param session
     * @param msg 已被解码的消息
     * @return  返回要被发送的响应
     */
    public Optional<Object> readerCallback(Session session, Object msg) ;
}
