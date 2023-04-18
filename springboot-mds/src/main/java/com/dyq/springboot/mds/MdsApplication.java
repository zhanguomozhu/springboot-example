package com.dyq.springboot.mds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = {"com.dyq.springboot.mds"})
public class MdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdsApplication.class, args);
    }

}
