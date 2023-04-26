package com.dyq.springboot.elasticjob.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dyq.springboot.elasticjob.listener.MyElasticJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyScriptListJobConfig {

    @Autowired
    private ScriptListJobConfig scriptListJobConfig;

    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    @Autowired
    private JobEventConfiguration jobEventConfiguration;


    @PostConstruct
    public void registerJob() {
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        System.out.println(scriptListJobConfig.getJobs().toString());
        scriptListJobConfig.getJobs().stream().forEach(job -> (new JobScheduler(registryCenter
                , getLiteJobConfiguration(job)
                , jobEventConfiguration, elasticJobListener)
        ).init());
    }

    private LiteJobConfiguration getLiteJobConfiguration(CommonScriptJob commonScriptJob) {
        JobCoreConfiguration.Builder builder = JobCoreConfiguration.newBuilder(commonScriptJob.getJobName()
                , commonScriptJob.getCron()
                , commonScriptJob.getShardingTotalCount());
        JobCoreConfiguration jobCoreConfiguration = builder
                .shardingItemParameters(commonScriptJob.getShardingItemParameters())
                .description(commonScriptJob.getJobDescription())
                .jobParameter(commonScriptJob.getJobParameter())
                .build();
        ScriptJobConfiguration scriptJobConfiguration = new ScriptJobConfiguration(jobCoreConfiguration
                , commonScriptJob.getScriptCommandLine());
        return LiteJobConfiguration
                .newBuilder(scriptJobConfiguration)
                .overwrite(true)
                .build();
    }
}