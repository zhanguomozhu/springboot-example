package com.dyq.springboot.mbpl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dyq.springboot.mbpl.mapper")
public class MbplApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbplApplication.class, args);
    }

}
