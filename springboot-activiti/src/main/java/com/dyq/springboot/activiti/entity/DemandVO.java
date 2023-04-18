package com.dyq.springboot.activiti.entity;

import lombok.Data;

/**
 * @Classname Demand
 * @Description TODO
 * @Date 2022/4/26 16:22
 * @Created by zrc
 */
@Data
public class DemandVO {

    //需求主键
    private String id;

    //需求名称
    private String name;

    //需求内容
    private String content;

    //任务id
    private String taskId;

    //1是保存2是修改
    private String type;

    //发起人id
    private String createuser;
}
