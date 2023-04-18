package com.dyq.springboot.mbsass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.dyq.springboot.mbsass.dao")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MbsassApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbsassApplication.class, args);
    }

}
