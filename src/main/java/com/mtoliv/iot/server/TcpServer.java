package com.mtoliv.iot.server;

import com.mtoliv.iot.utils.InitErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * Bootstrap - bootstrapping Netty
 *
 * EventLoopGroup - a group of EventLoop
 *
 * EventLoop - a loop that keeps looking for new events, e.g. incoming data from network sockets (from SocketChannel) instances).
 *             When an event occurs, the event is passed on to the appropriate event handler
 *
 * SocketChannel -  represents a TCP connection to another computer over a network.
 *
 * ChannelInitializer - nitialize the SocketChannel.
 *                      After initializing the SocketChannel the ChannelInitializer removes itself from the ChannelPipeline.
 *
 * ChannelPipeline - contains a list of ChannelHandler. When writing data out to a SocketChannel the written data is also
 *                   passed through the ChannelPipeline before finally being written to the SocketChannel
 *
 * ChannelHandler - handles the data that is received from a Netty SocketChannel.
 */
@Component
public class TcpServer {

    private final static Logger logger = LoggerFactory.getLogger(TcpServer.class);

    private final static String PROPERT_server_listen_port = "server.listen.port" ;

    @Autowired
    private ServerTransportConfig serverConfig;

    @Resource
    private Environment env;

    private int port;

    private static final int BIZ_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final int BIZ_THREAD_SIZE = 4;

    private ChannelFuture serverFuter ;

    public ChannelFuture getServerFuter() {
        return serverFuter;
    }

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZ_GROUP_SIZE);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZ_THREAD_SIZE);

    @PostConstruct
    public void init() throws Exception {
        logger.info("start server ...");

        port = env.getRequiredProperty(PROPERT_server_listen_port, Integer.class) ;

        // construct boss and worker threads (num threads = number of cores)
        //EventLoopGroup bossGroup = new NioEventLoopGroup();
        //EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.option(ChannelOption.SO_BACKLOG, 100);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new TcpServerCodecInitializer(serverConfig));

        logger.info("start server at port[" + port + "].");
        serverFuter = bootstrap.bind(port).sync();
        ChannelFuture channelFuture = serverFuter.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    logger.info("Server have success bind to " + port);
                } else {
                    logger.error("Server fail bind to " + port);
                    throw new InitErrorException("Server start fail !", future.cause());
                }
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        logger.info("shutdown server ...");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        logger.info("shutdown server end.");
    }

    public void setServerConfig(ServerTransportConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
