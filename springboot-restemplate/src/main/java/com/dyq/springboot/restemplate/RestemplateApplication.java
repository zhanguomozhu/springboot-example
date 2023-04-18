package com.dyq.springboot.restemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class RestemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestemplateApplication.class, args);
    }

}
