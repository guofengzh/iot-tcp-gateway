package com.mtoliv.iot.utils;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;

public class NetUtils {

    private final static Logger logger = LoggerFactory.getLogger(NetUtils.class);

    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        SocketAddress remote1 = ctx.channel().remoteAddress();
        InetSocketAddress remote = (InetSocketAddress) remote1;
        return NetUtils.toAddressString(remote);
    }

    public static String getLocalAddress(ChannelHandlerContext ctx) {
        SocketAddress local1 = ctx.channel().localAddress();
        InetSocketAddress local = (InetSocketAddress) local1;
        return NetUtils.toAddressString(local);
    }

    public static String getChannelSessionHook(ChannelHandlerContext ctx) {
        return ctx.channel().attr(Constants.ATTR_SERVER_SESSION).get();
    }

    public static void setChannelSessionHook(ChannelHandlerContext ctx, String sessionId) {
        ctx.channel().attr(Constants.ATTR_SERVER_SESSION).set(sessionId);
    }

    public static String toAddressString(InetSocketAddress address) {
        if (address == null) {
            return StringUtils.EMPTY;
        } else {
            return toIpString(address) + ":" + address.getPort();
        }
    }

    public static String toIpString(InetSocketAddress address) {
        if (address == null) {
            return null;
        } else {
            InetAddress inetAddress = address.getAddress();
            return inetAddress == null ? address.getHostName() :
                    inetAddress.getHostAddress();
        }
    }
}