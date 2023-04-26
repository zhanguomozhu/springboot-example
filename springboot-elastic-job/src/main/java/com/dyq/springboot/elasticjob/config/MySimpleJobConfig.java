package com.dyq.springboot.elasticjob.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dyq.springboot.elasticjob.job.MySimpleJob;
import com.dyq.springboot.elasticjob.listener.MyElasticJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySimpleJobConfig {

    /**
     * 任务名称
     */
    @Value("${simpleJob.mySimpleJob.name}")
    private String jobName;

    /**
     * cron表达式
     */
    @Value("${simpleJob.mySimpleJob.cron}")
    private String jobCron;

    /**
     * 作业分片总数
     */
    @Value("${simpleJob.mySimpleJob.shardingTotalCount}")
    private int jobShardingTotalCount;

    /**
     * 作业分片参数
     */
    @Value("${simpleJob.mySimpleJob.shardingItemParameters}")
    private String jobShardingItemParameters;

    /**
     * 自定义参数
     */
    @Value("${simpleJob.mySimpleJob.jobParameters}")
    private String jobParameters;

    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    @Bean
    public MySimpleJob mySimpleJob() {
        return new MySimpleJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MySimpleJob mySimpleJob) {
        //配置任务监听器
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        return new SpringJobScheduler(mySimpleJob, registryCenter, getLiteJobConfiguration(), elasticJobListener);
    }

    private LiteJobConfiguration getLiteJobConfiguration() {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobName, jobCron, jobShardingTotalCount).
                shardingItemParameters(jobShardingItemParameters).jobParameter(jobParameters).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MySimpleJob.class.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return simpleJobRootConfig;
 
    }
}