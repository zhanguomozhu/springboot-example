package com.dyq.springboot.elasticjob.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dyq.springboot.elasticjob.listener.MyElasticJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyScriptJobConfig {
 
    /**
     * 任务名称
     */
    @Value("${scriptJob.myScriptJob.name}")
    private String jobName;
 
    /**
     * cron表达式
     */
    @Value("${scriptJob.myScriptJob.cron}")
    private String jobCron;
 
    /**
     * 作业分片总数
     */
    @Value("${scriptJob.myScriptJob.shardingTotalCount}")
    private int jobShardingTotalCount;
 
    /**
     * 作业分片参数
     */
    @Value("${scriptJob.myScriptJob.shardingItemParameters}")
    private String jobShardingItemParameters;
 
    /**
     * 自定义参数
     */
    @Value("${scriptJob.myScriptJob.jobParameters}")
    private String jobParameters;
 
    @Autowired
    private ZookeeperRegistryCenter registryCenter;
 
    @Bean(initMethod = "init")
    public JobScheduler scriptJobScheduler() {
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        return new JobScheduler(registryCenter, getLiteJobConfiguration(), elasticJobListener);
    }
 
    private LiteJobConfiguration getLiteJobConfiguration() {
        // 定义作业核心配置
        JobCoreConfiguration scriptCoreConfig = JobCoreConfiguration.newBuilder(jobName, jobCron, jobShardingTotalCount).
                shardingItemParameters(jobShardingItemParameters).jobParameter(jobParameters).build();
        // 定义SCRIPT类型配置
        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(scriptCoreConfig, "D:/WorkSpace/java/github/springboot-example/springboot-elastic-job/src/main/resources/job.bat");
        // 定义Lite作业根配置
        LiteJobConfiguration scriptJobRootConfig = LiteJobConfiguration.newBuilder(scriptJobConfig).overwrite(true).build();
        return scriptJobRootConfig;

    }
}