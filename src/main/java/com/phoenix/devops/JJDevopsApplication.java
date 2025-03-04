package com.phoenix.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableAsync
@EnableCaching
@EnableWebSocket
@EnableScheduling
@SpringBootApplication
public class JJDevopsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JJDevopsApplication.class, args);
    }
}
