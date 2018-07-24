package com.mtoliv.iot.server.session;

import com.mtoliv.iot.server.session.listener.SessionEvent;
import com.mtoliv.iot.server.session.listener.SessionListener;
import com.mtoliv.iot.utils.Constants;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.mtoliv.iot.utils.NetUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private final static Logger logger = LoggerFactory.getLogger(SessionManager.class);

    @Resource
    private Environment env;

    private int maxInactiveInterval ;

    protected Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    public SessionManager() {
    }

    @PostConstruct
    public void init() {
        maxInactiveInterval = env.getRequiredProperty(Constants.PROPERT_session_expiration_second, Integer.class) ;
    }

    protected List<SessionListener> sessionListeners = null;

    public void setSessionListeners(List<SessionListener> sessionListeners) {
        this.sessionListeners = sessionListeners;
    }

    public synchronized Session createSession(ChannelHandlerContext ctx) {
        String sessionId = makeSessionId(ctx) ;
        Session session = sessions.get(sessionId);
        if (session != null) {
            logger.debug("session " + sessionId + " exist, remove it!");
            sessions.remove(sessionId) ;
        }
        logger.debug("create new session " + sessionId + ", ctx -> " + ctx.toString());

        session = new Session(ctx);
        session.setSessionId(makeSessionId(ctx));
        session.setMaxInactiveInterval(this.getMaxInactiveInterval());
        session.setCreationTime(System.currentTimeMillis());
        session.setLastAccessedTime(System.currentTimeMillis());
        session.setSessionManager(this);

        for (SessionListener listener : sessionListeners) {
            session.addSessionListener(listener);
        }

        this.addSession(session);

        fireSessionCreated(session);

        logger.debug("create new session " + sessionId + " successful!");

        return session;
    }

    public synchronized void addSession(Session session) {
        if (null == session) {
            return;
        }
        sessions.put(session.getSessionId(), session);
        logger.debug("put a session " + session.getSessionId() + " to sessions!");
    }

    public synchronized void updateSession(String sessionId) {
        Session session = sessions.get(sessionId);
        session.setLastAccessedTime(System.currentTimeMillis());

        sessions.put(sessionId, session);
    }

    public void updateSession(ChannelHandlerContext ctx) {
        String sessionId = makeSessionId(ctx) ;
        this.updateSession(sessionId);
    }

    /**
     * Remove this Session from the active Sessions for this Manager.
     */
    public synchronized void removeSession(String sessionId) {
        fireSessionDestroyed(sessions.get(sessionId)) ;
        sessions.remove(sessionId);
        logger.debug("remove the session " + sessionId + " from sessions!");
    }

    public synchronized void removeSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session is null!");
        }
        removeSession(session.getSessionId());
    }

    public synchronized void removeSession(ChannelHandlerContext ctx) {
        String sessionId = makeSessionId(ctx) ;
        removeSession(sessionId);
    }

    public void fireSessionCreated(Session session) {
        if (sessionListeners != null ) {
            SessionEvent event = new SessionEvent(session);
            for (SessionListener listener : sessionListeners) {
                try {
                    listener.sessionCreated(event);
                    logger.info("SessionListener " + listener + " .sessionCreated() is invoked successfully!");
                } catch (Exception e) {
                    logger.error("addSessionEvent error.", e);
                }
            }
        }
    }

    public void fireSessionDestroyed(Session session) {
        if (sessionListeners != null ) {
            SessionEvent event = new SessionEvent(session);
            for (SessionListener listener : sessionListeners) {
                try {
                    listener.sessionDestroyed(event);
                } catch (Exception e) {
                    logger.error("addSessionEvent error.", e);
                }
            }
        }
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public Session getSession(ChannelHandlerContext ctx) {
        String sessionId = makeSessionId(ctx) ;
        return sessions.get(sessionId);
    }

    public Session[] getSessions() {
        return sessions.values().toArray(new Session[0]);
    }

    public Set<String> getSessionKeys() {
        return sessions.keySet();
    }

    public int getSessionCount() {
        return sessions.size();
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    private String makeSessionId(ChannelHandlerContext ctx) {
        String address = getRemoteAddress(ctx) ;
        return address ;
    }

    private String getRemoteAddress(ChannelHandlerContext ctx) {
        SocketAddress remote1 = ctx.channel().remoteAddress();
        InetSocketAddress remote = (InetSocketAddress) remote1;
        return NetUtils.toAddressString(remote);
    }
}
