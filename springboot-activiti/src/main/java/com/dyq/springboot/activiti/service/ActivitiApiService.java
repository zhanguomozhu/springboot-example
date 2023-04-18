package com.dyq.springboot.activiti.service;

import com.dyq.springboot.activiti.entity.Demand;
import com.dyq.springboot.activiti.entity.DemandVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (Demand)表服务接口
 *
 * @author makejava
 * @since 2023-04-17 12:09:46
 */
public interface ActivitiApiService {
    /**
     * 保存需求问题
     * @param demand 保存需求传入参数
     */
    int saveDemand(Demand demand);

    /**
     * 查看详情
     * @param id 业务主键id
     * @return
     */
    Demand getDemandDetail(String id);

    /**
     * 开启流程
     * @param uid 当前人用户id
     * @param taskId 任务id
     */
    int start(String uid, String taskId);

    /**
     * 查看需求问题集合
     * @return
     */
    List<Demand> getDemandList();

    /**
     * 查看审核人集合
     * @param taskId 任务id
     * @param type 1是通过，0是退回
     * @return
     */
    Map<String, Object> getAssigneeList(String taskId, String type);

    /**
     * 查看流程记录
     * @param taskId 任务id
     * @return
     */
    ArrayList<Map<String, Object>> getProcessList(String taskId);

    /**
     * 提交流程
     */
    int taskSubmit(String taskId, String type, String assignee);

    /**
     * 获取未结束流程图数据图
     * @param processInstanceId
     * @return
     */
    InputStream getProcessDiagram(String processInstanceId);

    /**
     * 获取历史已结束流程图数据图
     * @param processInstanceId
     * @return
     */
    InputStream getHistoryProcessDiagram(String processInstanceId);


    /**
     * 待办列表
     * @param uid
     * @return
     */
    List<Map<String, String>> toDoTaskList(@RequestParam String uid);
}

