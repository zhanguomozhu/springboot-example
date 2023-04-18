package com.dyq.springboot.starter.domain;
 
 
import org.springframework.boot.context.properties.ConfigurationProperties;
 
/**
 * 描述：配置信息 实体
 *
 **/
@ConfigurationProperties(prefix = "demo")
public class DemoProperties {
    private String params1;
    private String params2;
    private Integer minLength;
 
 
    public String getParams1() {
        return params1;
    }
 
    public void setParams1(String params1) {
        this.params1 = params1;
    }
 
    public String getParams2() {
        return params2;
    }
 
    public void setParams2(String params2) {
        this.params2 = params2;
    }
 
    public Integer getMinLength() {
        return minLength;
    }
 
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }
}