package com.dyq.springboot.quart.controller;


import com.dyq.springboot.quart.entity.Params;
import com.dyq.springboot.quart.service.QuartzService;
import com.dyq.springboot.quart.utils.BaseControllerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping(value = "/quartz")
public class QuartController{
    Logger logger = LoggerFactory.getLogger(QuartController.class);

    @SuppressWarnings("all")
    @Autowired
    QuartzService quartzService;

    @SuppressWarnings("all")
    @RequestMapping("/addjob")
    public void startJob(@Valid @RequestBody Params param, HttpServletResponse response, HttpServletRequest request) {
        Map map = quartzService.startaddJob(request, response, param);
        BaseControllerUtil.renderResult(response, map);
    }

    @SuppressWarnings("all")
    @RequestMapping("/updatejob")
    public void updatejob(@RequestBody Map param, HttpServletResponse response) {
        logger.info("更新任务");
        String jobName = param.get("jobName").toString();
        String jobGroupName = param.get("jobGroupName").toString();
        String cron = param.get("cron").toString();
        quartzService.updateJob(jobName, jobGroupName, cron);
        BaseControllerUtil.renderResult(response, "更新成功");
    }

    @SuppressWarnings("all")
    @RequestMapping("/deletejob")
    public void deletejob(@RequestBody Map param, HttpServletResponse response) {
        logger.info("任务删除");
        String jobName = param.get("jobName").toString();
        String jobGroupName = param.get("jobGroupName").toString();
        quartzService.deleteJob(jobName, jobGroupName);
        BaseControllerUtil.renderResult(response, "删除成功");
    }

    @SuppressWarnings("all")
    @RequestMapping("/pauseJob")
    public void pauseJob(@RequestBody Map param, HttpServletResponse response) {
        logger.info("任务暂停");
        String jobName = param.get("jobName").toString();
        String jobGroupName = param.get("jobGroupName").toString();
        quartzService.pauseJob(jobName, jobGroupName);
        BaseControllerUtil.renderResult(response, "暂停成功");
    }

    @SuppressWarnings("all")
    @RequestMapping("/resumeJob")
    public void resumeJob(@RequestBody Map param, HttpServletResponse response) {
        logger.info("重新开始任务");
        String jobName = param.get("jobName").toString();
        String jobGroupName = param.get("jobGroupName").toString();
        quartzService.resumeJob(jobName, jobGroupName);
        BaseControllerUtil.renderResult(response, "重新开始任务");
    }

    @SuppressWarnings("all")
    @RequestMapping("/queryAllJob")
    public Object queryAllJob() {
        logger.info("查询所有任务");
        return quartzService.queryAllJob();
    }

    @SuppressWarnings("all")
    @RequestMapping("/queryRunJob")
    public Object queryRunJob() {
        logger.info("查询运行中的任务");
        return quartzService.queryRunJob();
    }

    @SuppressWarnings("all")
    @RequestMapping("/RunOnec")
    public void RunOnec(@RequestBody Map param, HttpServletResponse response) {
        logger.info("运行一次任务");
        String jobName = param.get("jobName").toString();
        String jobGroupName = param.get("jobGroupName").toString();
        quartzService.runAJobNow(jobName,jobGroupName);
        BaseControllerUtil.renderResult(response, "运行一次任务");
    }
}


