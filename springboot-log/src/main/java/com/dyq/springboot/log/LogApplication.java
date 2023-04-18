package com.dyq.springboot.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {

        //下面语句使得Log4j2日志输出使用异步处理，减小输出日志对性能的影响
//      System.setProperty("Log4jContextSelector","org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        // 命令行启动一步
//      java -jar -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector xxx-xxx-1.0.jar
        SpringApplication.run(LogApplication.class, args);
    }

}
