package com.dyq.springboot.elasticjob.config;

import lombok.*;

@Data
public class CommonScriptJob {
    private String cron;
    private int shardingTotalCount;
    private String shardingItemParameters;
    private String jobDescription;
    private String jobParameter;
    private String jobName;
    private String scriptCommandLine;
}

