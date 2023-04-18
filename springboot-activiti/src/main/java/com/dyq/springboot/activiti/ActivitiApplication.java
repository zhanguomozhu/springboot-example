package com.dyq.springboot.activiti;

import org.activiti.core.common.spring.identity.config.ActivitiSpringIdentityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
//@SpringBootApplication(exclude = {ActivitiSpringIdentityAutoConfiguration.class})
@SpringBootApplication(exclude ={SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
