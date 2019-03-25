package com.krystianzybala.rxspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class RxspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RxspringApplication.class, args);
    }

}
