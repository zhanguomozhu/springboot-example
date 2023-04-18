package com.dyq.springboot.starter.config;
 

import com.dyq.springboot.starter.domain.DemoProperties;
import com.dyq.springboot.starter.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * 描述：配置类
 *
 **/
@Configuration
@EnableConfigurationProperties(DemoProperties.class)
@ConditionalOnProperty(
        prefix = "demo",
        name = "enable",
        havingValue = "true"
)
public class DemoStarterAutoConfig {
    @Autowired
    private DemoProperties demoProperties;
 
    @Bean(name = "demo")
    public DemoService demoService(){
        return new DemoService(demoProperties.getParams1(), demoProperties.getParams2());
    }
}