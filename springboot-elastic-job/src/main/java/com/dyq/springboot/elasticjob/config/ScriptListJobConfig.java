package com.dyq.springboot.elasticjob.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix ="script-job-list")
public class ScriptListJobConfig {

    /**包含打印机配置文件的数组*/
    private List<CommonScriptJob> jobs;
}