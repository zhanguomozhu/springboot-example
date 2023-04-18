package com.dyq.springboot.quart.service;


import com.alibaba.fastjson.JSONObject;

import com.dyq.springboot.quart.entity.Params;
import com.dyq.springboot.quart.utils.QuartzreflectUtil;
import com.dyq.springboot.quart.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class QuartzService {

    @Qualifier("schedulerFactoryBean")
    @Autowired
    private Scheduler scheduler;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");


    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    @Transactional
    public Map startaddJob(HttpServletRequest request, HttpServletResponse response, Params param){
        Map<String,Object> res = new HashMap();
        String className = param.getClassName();        //获取需要执行方法的类名
        String jobName = param.getJobName();
        String jobGroupName = param.getJobGroupName();
        String cron = param.getCron();
        if(param.getParamData()!=null && param.getParamData().size()>0){
            param = integratedData(param, response, request);
        }
        Map map = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
        map.put("finalMethodParam",param.getFinalMethodParam());
        map.put("class", className);
        Class<?> bean = null;
        try {
            bean = Class.forName(SpringContextUtil.getBean(className).getClass().getCanonicalName());
        } catch (ClassNotFoundException e) {
            log.error("找不到当前类");
            e.printStackTrace();
        }
        String date = addJob(bean, jobName, jobGroupName, cron, map);
        log.info("任务添加成功");
        res.put("msg", date);
        return res;
    }

    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     * @param jobTime
     *            时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes
     *            运行的次数 （<0:表示不限次数）
     */
    @SuppressWarnings("all")
    public String addJob(Class <? extends Job> jobClass, String jobName, String jobGroupName, int jobTime,
                       int jobTimes) {
        String date = null;
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)// 任务名称和组构成任务key
                    .build();
            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger().withIdentity(jobName, jobGroupName).withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow().build();
            }
            Date date1 =  scheduler.scheduleJob(jobDetail, trigger);
            date = sdf.format(date1);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return date;
    }
    @SuppressWarnings("all")
    public String addJob(Class jobClass, String jobName, String jobGroupName, String jobTime,Map param) {
        String date = null;

        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                    .usingJobData(new JobDataMap(param))// 任务名称和组构成任务key
                    .build();
            // 定义调度触发规则
            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)// 触发器key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .usingJobData(new JobDataMap(param))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startNow().build();
            // 把作业和触发器注册到任务调度中
            Date date1 = scheduler.scheduleJob(jobDetail, trigger);
            date = sdf.format(date1);
        } catch (Exception e) {
            date = e.getMessage();
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     * @param jobTime
     *            时间表达式 （如：0/5 * * * * ? ）
     */
    @SuppressWarnings("all")
    public String addJob(Class <? extends Job> jobClass, String jobName, String jobGroupName, String jobTime) {
        String date = null;
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)// 任务名称和组构成任务key
                    .build();
            // 定义调度触发规则
            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)// 触发器key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startNow().build();
            // 把作业和触发器注册到任务调度中
            Date date1 = scheduler.scheduleJob(jobDetail, trigger);
            date = sdf.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName   任务名
     * @param jobGroupName  分组名
     * @param jobTime   执行时间
     */
    public void updateJob(String jobName, String jobGroupName, String jobTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务一个job
     *
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName   任务名
     * @param jobGroupName  分组名
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobName   任务名
     * @param jobGroupName  分组名
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即执行一个job
     *
     * @param jobName       任务名
     * @param jobGroupName  分组名
     */
    public void runAJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    @SuppressWarnings("all")
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    map.put("className", trigger.getJobDataMap().getString("className"));
                    map.put("classMethod", trigger.getJobDataMap().getString("method"));
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    @SuppressWarnings("all")
    public List<Map<String, Object>> queryRunJob() {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    @SuppressWarnings("all")
    public Params integratedData(Params param, HttpServletResponse response,HttpServletRequest request){         //填充全类名
        List list = QuartzreflectUtil.inputParamListOptimize(param, response, request);
        if (list!=null && list.size()>0){
            Object[] obj2 = new Object[list.size()];             //创建一个以参数长度一样的对象数组
            QuartzreflectUtil.setObjelement(list, obj2);
            param.setFinalMethodParam(obj2);
        }
        return param;
    }

}



