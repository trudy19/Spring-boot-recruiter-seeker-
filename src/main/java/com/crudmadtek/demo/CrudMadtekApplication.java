package com.crudmadtek.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class CrudMadtekApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudMadtekApplication.class, args);
    }

}
