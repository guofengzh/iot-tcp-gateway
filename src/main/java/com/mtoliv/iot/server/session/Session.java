package com.mtoliv.iot.server.session;

import com.mtoliv.iot.server.session.listener.SessionEvent;
import com.mtoliv.iot.server.session.listener.SessionListener;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Session {

    private final static Logger logger = LoggerFactory.getLogger(Session.class);
    /**
     * The session identifier of this Session.
     */
    private String sessionId = null;

    /**
     * The session is in active
     */
    private boolean active = false ;

    /**
     * The time this session was created, in milliseconds since midnight,
     * January 1, 1970 GMT.
     */
    protected long creationTime = 0L;

    /**
     * The last accessed time for this Session.
     */
    protected volatile long lastAccessedTime = creationTime;

    /**
     * The maximum time interval, in seconds, between client requests before the
     * container may invalidate this session. A negative time indicates that the
     * session should never time out.
     */
    protected int maxInactiveInterval ;

    private transient List<SessionListener> listeners = new CopyOnWriteArrayList<SessionListener>();

    private ChannelHandlerContext ctx ;

    /**
     * The Manager with which this Session is associated.
     */
    private SessionManager sessionManager = null;

    public Session(ChannelHandlerContext ctx) {
        this.ctx = ctx ;
    }

    public CompletionStage<Void> send(Object response) {
        CompletableFuture<Void> stage = new CompletableFuture<>();
        if (!isValid()) {
            stage.completeExceptionally(new SessionInvalidException(this.sessionId));
        } else {
            ChannelFuture future = ctx.channel().writeAndFlush(response).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        stage.complete(null);
                    } else {   // more test?
                        stage.completeExceptionally(future.cause());
                    }
                }
            });
        }

        return stage ;
    }

    public boolean isValid() {
        return active && !expire() ;
    }

    /**
     * Add a session event listener to this component.
     */
    public void addSessionListener(SessionListener listener) {
        if (null == listener) {
            throw new IllegalArgumentException("addSessionListener listener");
        }
        listeners.add(listener);
    }

    /**
     * Remove a session event listener from this component.
     */
    public void removeSessionListener(SessionListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("removeSessionListener listener");
        }
        listeners.remove(listener);
    }

    public boolean expire() {
        //A negative time indicates that the session should never time out.
        if (maxInactiveInterval < 0)
            return false;

        long timeNow = System.currentTimeMillis();
        int timeIdle = (int) ((timeNow - lastAccessedTime) / 1000L);
        if (timeIdle >= maxInactiveInterval) {
            return true;
        }
        return false;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
