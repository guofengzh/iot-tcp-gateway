package com.mtoliv.iot;

import com.mtoliv.iot.server.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Server implements CommandLineRunner  {
    private final static Logger logger = LoggerFactory.getLogger(Server.class);

    @Autowired
    protected TcpServer tcpServer ;

    @Override
    public void run(String... args) throws Exception {
        // block until the server socket is closed.
        tcpServer.getServerFuter().channel().closeFuture().sync();
    }
}

