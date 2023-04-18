package com.dyq.springboot.starter.service;

import com.dyq.springboot.starter.domain.DemoProperties;
import org.springframework.beans.factory.annotation.Autowired;
 
/**
 * author:HUAWEI
 */
public class DemoService {
 
    @Autowired
    private DemoProperties demoProperties;
 
    public String params1;
    public String params2;
 
    public DemoService(String param1, String param2) {
        this.params1 = param1;
        this.params2 = param2;
    }
 
    public String paramsInfo() {
        return this.params1 + "!  " + params2;
    }
 
    public boolean isValidLength(String param) {
 
        int length = param.length();
        Integer minLength = demoProperties.getMinLength();
 
        if (length < minLength.intValue()) {
            return false;
        } else {
            return true;
        }
 
    }
}