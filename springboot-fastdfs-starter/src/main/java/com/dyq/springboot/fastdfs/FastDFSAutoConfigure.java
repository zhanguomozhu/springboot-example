package com.dyq.springboot.fastdfs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现最终目标把FastDFSClientUtil自动注入Spring，提供外部使用
* 使用方式 @Resource、 @Autowired
 */
@Configuration
//当classpath下面有这三个类才做自动装配
@ConditionalOnClass(value = {FastDFSClientFactory.class,FastDFSClientPool.class,FastDFSClientUtil.class})
@EnableConfigurationProperties(FastDFSProperties.class)
public class FastDFSAutoConfigure {

    private final FastDFSProperties properties;

    @Autowired
    public FastDFSAutoConfigure(FastDFSProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
        //当没有FastDFSClientUtil，就把FastDFSClientUtil作为Bean注入Spring
    FastDFSClientUtil fastDFSClientUtil (){
        return new FastDFSClientUtil(properties);
    }

}