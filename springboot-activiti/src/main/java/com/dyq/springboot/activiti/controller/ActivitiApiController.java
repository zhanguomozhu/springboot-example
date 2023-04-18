package com.dyq.springboot.activiti.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyq.springboot.activiti.common.CommonResult;
import com.dyq.springboot.activiti.dao.UserDao;
import com.dyq.springboot.activiti.entity.Demand;
import com.dyq.springboot.activiti.entity.DemandVO;
import com.dyq.springboot.activiti.entity.User;
import com.dyq.springboot.activiti.service.ActivitiApiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Wrapper;
import java.util.*;

@RestController
@RequestMapping("/activiti")
@Slf4j
public class ActivitiApiController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

//    /**
//     * 启动请假流程
//     **/
//    @GetMapping("/start")
//    public void start() {
//        String instanceKey = "demand";
//        System.out.println("开启请假流程...");
//        Map<String, Object> map = new HashMap<String, Object>();
//        //在holiday.bpmn中,填写请假单的任务办理人为动态传入的userId,此处模拟一个id
//        map.put("userId", "10001");
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceKey, map);
//        System.out.println("启动流程实例成功: "+instance);
//        System.out.println("流程实例ID: "+instance.getId());
//        System.out.println("流程定义ID: "+instance.getProcessDefinitionId());
//    }
//
//    /**
//     * 员工申请
//     **/
//    @GetMapping("/employeeApply")
//    public void employeeApply() {
//        String instanceId = "d3822dcd-dcc7-11ed-82a7-44e517d73233"; // 任务ID
//        String leaveDays = "18"; // 请假天数
//        String leaveReason = "回家结婚"; // 请假原因
//        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
//        if (task == null) {
//            System.out.println("任务ID: "+instanceId+" 查询到任务为空！");
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("days", leaveDays);
//        map.put("date", new Date());
//        map.put("reason", leaveReason);
//        taskService.complete(task.getId(), map);
//        System.out.println("执行【员工申请】环节，流程推动到【上级审核】环节");
//    }
//
//    /**
//     * 查看请假信息
//     **/
//    @GetMapping("/showTaskInfo")
//    public void showTaskInfo (){
//        String instanceId = "d3822dcd-dcc7-11ed-82a7-44e517d73233"; // 任务ID
//
//        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
//        String days = (String) taskService.getVariable(task.getId(), "days");
//        Date date = (Date) taskService.getVariable(task.getId(), "date");
//        String reason = (String) taskService.getVariable(task.getId(), "reason");
//        String userId = (String) taskService.getVariable(task.getId(), "userId");
//        System.out.println("请假天数:  " + days);
//        System.out.println("请假理由:  " + reason);
//        System.out.println("请假人id:  " + userId);
//        System.out.println("请假日期:  " + date.toString());
//    }
//
//
//    /**
//     * 领导审批
//     **/
//    @GetMapping("/departmentAudit")
//    public void departmentAudit() {
//        String instanceId = "d3822dcd-dcc7-11ed-82a7-44e517d73233"; // 任务ID
//        String departmentalOpinion = "恭喜恭喜，早生贵子";
//        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("departmentalOpinion", departmentalOpinion);
//        taskService.complete(task.getId(), map);
//        System.out.println("添加审批意见,请假流程结束");
//    }

    @Resource
    private UserDao userDao;
    @Resource
    private ActivitiApiService activitiApiService;

    @GetMapping("/loginAction")
    public CommonResult loginAction(@RequestParam("username") String username) {
        System.out.println(11111);
        if(StringUtils.isNotEmpty(username)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userDao.selectOne(queryWrapper);
            System.out.println(user.toString());
            return CommonResult.success(user);
        }else{
            return CommonResult.failed("登录失败");
        }
    }

    //新增任务
    @CrossOrigin
    @PostMapping("/saveDemand")
    private CommonResult saveDemand(@RequestBody Demand demand){
        log.info("新增任务");
        int bool = activitiApiService.saveDemand(demand);
        if (bool > 0) {
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }

    //开启流程
    @CrossOrigin
    @PostMapping("/start")
    private CommonResult start(@RequestParam("uid") String uid,@RequestParam("taskId") String taskId){
        log.info("开启流程");
        int bool = activitiApiService.start(uid,taskId);
        if (bool > 0) {
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }


    //提交流程
    @CrossOrigin
    @PostMapping("/taskSubmit")
    public CommonResult taskSubmit(@RequestParam("taskId") String taskId,@RequestParam("type") String type,@RequestParam("assignee") String assignee){
        log.info("提交流程");
        int bool = activitiApiService.taskSubmit(taskId,type,"undefined".equals(assignee)?"":assignee);
        if (bool > 0) {
            return CommonResult.success("操作成功");
        }else {
            return CommonResult.failed("操作失败");
        }
    }

    //查看需求集合
    @CrossOrigin
    @PostMapping("/getDemandList")
    private CommonResult getDemandList(){
        log.info("需求列表");
        return CommonResult.success(activitiApiService.getDemandList());
    }

    //在审核界面，根据传入的任务id和按钮名称返回前端具体的流程详情和下一步审批人列表
    @CrossOrigin
    @PostMapping("/getAssigneeList")
    private CommonResult getAssigneeList(@RequestParam String taskId, @RequestParam String type){
        log.info("审核人列表");
        return CommonResult.success(activitiApiService.getAssigneeList(taskId,type));
    }

    //查看流程记录
    @CrossOrigin
    @PostMapping("/getProcessList")
    private CommonResult getProcessList(@RequestParam String taskId){
        log.info("查看流程记录");
        return CommonResult.success(activitiApiService.getProcessList(taskId));
    }

    //查看详情
    @CrossOrigin
    @PostMapping("/getDemandDetail")
    private CommonResult getDemandDetail(@RequestParam("id") String id){
        log.info("查看详情");
        return CommonResult.success(activitiApiService.getDemandDetail(id));
    }

    //查看流程图
    @CrossOrigin
    @GetMapping("/getProcessPic")
    private ResponseEntity<byte[]> getProcessPic(@RequestParam("taskId") String taskId) throws IOException {
        log.info("查看流程图");
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(taskId).singleResult();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        InputStream in;
        if(task == null){//历史流程/已结束的流程
            in = activitiApiService.getHistoryProcessDiagram(taskId);
        }else{//正在执行的流程查询
            in = activitiApiService.getProcessDiagram(task.getProcessInstanceId());
        }

        return new ResponseEntity<>(getBytesByStream(in),headers, HttpStatus.OK);
    }

    /**
     * 输出字符流
     * @param inputStream
     * @return
     */
    public byte[]  getBytesByStream(InputStream inputStream){
        byte[] bytes = new byte[1024];
        int b;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while((b = inputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes,0,b);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //查看代待办
    @CrossOrigin
    @PostMapping("/toDoTaskList")
    public CommonResult toDoTaskList(@RequestParam String uid){
        log.info("查看代待办");
        return CommonResult.success(activitiApiService.toDoTaskList(uid));
    }
}