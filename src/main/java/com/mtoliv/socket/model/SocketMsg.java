package com.mtoliv.socket.model;

import java.io.Serializable;

public class SocketMsg implements Serializable {

	private static final long serialVersionUID = -548505853531960234L;
	private String addressIP;
	private int port;
	private String msg;
	private long receiveTime;
	private String replyMsg;
	private String serverId;
	
	public SocketMsg(String addressIP, int port, String msg) {
		super();
		this.addressIP = addressIP;
		this.port = port;
		this.msg = msg;
		this.receiveTime = System.currentTimeMillis();
	}
	
	public SocketMsg(String serverId, String msg, String replyMsg) {
        super();
        this.serverId = serverId;
        this.msg = msg;
        this.replyMsg = replyMsg;
        this.receiveTime = System.currentTimeMillis();
    }
	
	public String getAddressIP() {
		return addressIP;
	}
	public void setAddressIP(String addressIP) {
		this.addressIP = addressIP;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(long receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getReplyMsg() {
        return replyMsg;
    }
    public void setReplyMsg(String replyMsg) {
        this.replyMsg = replyMsg;
    }
    public String getServerId() {
        return serverId;
    }
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    
    @Override
    public String toString() {
        return "SocketMsg [addressIP=" + addressIP + ", port=" + port + ", msg=" + msg + ", receiveTime=" + receiveTime
                + ", replyMsg=" + replyMsg + ", serverId=" + serverId + "]";
    }
}
