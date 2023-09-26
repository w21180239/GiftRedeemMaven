package com.wej.giftredeem.point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PointServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PointServiceApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
        app.run(args);
    }
}
