package com.dyq.springboot.activiti.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Demand)表实体类
 *
 * @author makejava
 * @since 2023-04-17 12:09:46
 */
@SuppressWarnings("serial")
public class Demand{
    //主键
    private String id;
    //流程状态，-1是未启动,0是填写表单，1是组长审批，2是开发经理审批，3是项目经理审批，4是结束
    private String status;
    //需求名称
    private String name;
    //需求具体内容
    private String content;
    //盐字段
    private String salt;
    //任务id
    private String taskid;
    //发起人
    private String createuser;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
}

