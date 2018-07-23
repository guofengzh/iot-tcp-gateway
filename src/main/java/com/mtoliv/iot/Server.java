package com.mtoliv.iot;

import com.mtoliv.iot.server.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.mtoliv.iot"})
public class Server  {
    private final static Logger logger = LoggerFactory.getLogger(Server.class);

    //@Autowired
    protected TcpServer tcpServer ;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}

