package com.mtoliv.iot.server;

import com.mtoliv.iot.server.api.TcpReader;
import com.mtoliv.iot.server.api.TcpWirter;
import com.mtoliv.iot.server.session.SessionManager;
import com.mtoliv.iot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class TcpServerTransportConfig {

    @Autowired
    private SessionManager sessionManager = null;

    @Autowired
    private TcpReader reader = null;

    @Autowired
    TcpWirter wirter;

    @Resource
    private Environment env;

    private int channelReadIdleSeconds;
    private int getChannelWriteIdleSeconds;

    @PostConstruct
    public void init() {
        channelReadIdleSeconds = env.getRequiredProperty(Constants.PROPERT_channel_read_idle_seconds, Integer.class) ;
        getChannelWriteIdleSeconds = env.getRequiredProperty(Constants.PROPERT_channel_write_idle_seconds, Integer.class) ;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public TcpReader getReader() {
        return reader;
    }

    public TcpWirter getWirter() {
        return wirter;
    }

    public int getChannelReadIdleSeconds() {
        return channelReadIdleSeconds;
    }

    public void setChannelReadIdleSeconds(int channelReadIdleSeconds) {
        this.channelReadIdleSeconds = channelReadIdleSeconds;
    }

    public int getGetChannelWriteIdleSeconds() {
        return getChannelWriteIdleSeconds;
    }

    public void setGetChannelWriteIdleSeconds(int getChannelWriteIdleSeconds) {
        this.getChannelWriteIdleSeconds = getChannelWriteIdleSeconds;
    }
}
