package com.dyq.springboot.activiti.service.impl;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyq.springboot.activiti.dao.DemandDao;
import com.dyq.springboot.activiti.dao.UserDao;
import com.dyq.springboot.activiti.entity.Demand;
import com.dyq.springboot.activiti.entity.User;
import com.dyq.springboot.activiti.service.ActivitiApiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivitiApiServiceImpl implements ActivitiApiService {

    @Resource
    private DemandDao demandDao;

    @Resource
    private UserDao userDao;


    //  启动流程及对流程数据的控制
    //  流程实例(ProcessInstance)与执行流(Execution)的查询
    //  触发流程操作,接收消息和信号
    @Resource
    RuntimeService runtimeService;

    //  对用户任务UserTask的管理和流程的控制
    //  设置用户任务的权限信息（设置候选人等）
    //  针对用户任务添加任务附件，任务评论和事件记录
    @Resource
    TaskService taskService;

    @Resource
    HistoryService historyService;

    @Resource
    RepositoryService repositoryService;


    @Override
    public int saveDemand(Demand demand) {
        if(StringUtils.isBlank(demand.getId())){//新增
            demand.setStatus("-1");
            String uuid = IdUtil.randomUUID();
            demand.setTaskid(uuid);
            return demandDao.insert(demand);
        }else {//修改
            QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",demand.getId());
            return demandDao.update(demand,queryWrapper);
        }
    }

    @Override
    public Demand getDemandDetail(String id) {
        QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return demandDao.selectOne(queryWrapper);
    }

    @Override
    public int start(String uid, String taskId) {
        //查询是否有流程记录有无需启动流程
        List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(taskId).list();
        if (taskList != null && taskList.size() > 0) {
            return 0;
        }
        //启动流程
        String processId = "demand";
        this.deployProcessByProcessesName("demand.bpmn20.xml");
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0", uid);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processId, taskId, variables);
        log.info(taskId + "流程启动成功，流程id:" + pi.getId());

        //更新状态
        QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("taskid",taskId);
        Demand demand = new Demand();
        demand.setStatus("0");
        return demandDao.update(demand,queryWrapper);
    }

    /**
     * 部署流程服务
     * @return
     */
    public void deployProcessByProcessesName(String processesName){
        // 根据bpmn文件部署流程
        Deployment deployment = repositoryService.createDeployment().name(processesName).addClasspathResource("processes/" + processesName)
                .deploy();
        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        String processId = processDefinition.getId();
        log.info("初始化流程==processId==" + processId);
    }


    @Override
    public List<Demand> getDemandList() {
        QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
        return demandDao.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> getAssigneeList(String taskId, String type) {
        //1. 先获取流程
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(taskId).singleResult();
        // 判断流程是否存在
        if (task == null) {
            log.info("没有查询到任务");
            return null;
        }
        // 创建保存最终结果对象
        Map<String, Object> resultMap = new HashMap<>();
        //流程数据
        Map<String, String> flowMap = new HashMap<>();
        switch (task.getTaskDefinitionKey()) {
            case "writeform": //填写表单
                log.info("填写表单");
                //包装流程数据
                flowMap.put("flowName", "开发组长审核");
                flowMap.put("flowId", "shenhe1");
                resultMap.put("flowInfo", flowMap);
                // 根据业务id获取组长列表
                resultMap.put("userInfo", demandDao.getKaiFaZuZhangList());
                break;
            case "shenhe1": //组长审核
                log.info("组长审核");
                //  如果是点击的通过按钮
                if(type.equals("1")){//通过
                    //  包装流程数据
                    flowMap.put("flowName", "开发经理审核");
                    flowMap.put("flowId", "shenhe2");
                    resultMap.put("flowInfo", flowMap);
                    // 根据业务id获取经理列表
                    resultMap.put("userInfo", demandDao.getKaiFaJingLiList());
                }else{//驳回
                    //  如果是点击的退回按钮
                    //  包装流程数据
                    flowMap.put("flowName", "表单填写");
                    flowMap.put("flowId", "writeform");
                    resultMap.put("flowInfo", flowMap);
                    //  获取流程发起人的id
                    String uid = getInitiator(taskId);
                    User user = new User();
                    user.setId(uid);
                    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
                    queryWrapper.eq("id",uid);
                    resultMap.put("userInfo", userDao.selectList(queryWrapper));
                }
                break;
            case "shenhe2": //开发经理审核
                log.info("开发经理审核");
                //  如果是点击的通过按钮
                if(type.equals("1")){//通过
                    flowMap.put("flowName", "项目经理审核");
                    flowMap.put("flowId", "shenhe3");
                    resultMap.put("flowInfo", flowMap);
                    // 根据业务id获取经理列表
                    resultMap.put("userInfo", demandDao.getProjectJingLiList());
                }else{//驳回
                    //  如果是点击的退回按钮
                    flowMap.put("flowName", "表单填写");
                    flowMap.put("flowId", "writeform");
                    resultMap.put("flowInfo", flowMap);
                    //  获取流程发起人的id
                    String uid = getInitiator(taskId);
                    User user = new User();
                    user.setId(uid);
                    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
                    queryWrapper.eq("id",uid);
                    user.setUsername(userDao.selectOne(queryWrapper).getUsername());
                    List<User> userList = new ArrayList<>();
                    userList.add(user);
                    resultMap.put("userInfo", userList);
                }
                break;
            case "shenhe3": //项目经理审核
                log.info("项目经理审核");
                if(type.equals("1")){//通过
                    flowMap.put("flowName", "流程结束");
                    flowMap.put("flowId", "end_task");
                    resultMap.put("flowInfo", flowMap);
                    resultMap.put("userInfo", null);
                }else{//驳回
                    flowMap.put("flowName", "表单填写");
                    flowMap.put("flowId", "writeform");
                    resultMap.put("flowInfo", flowMap);
                    //  获取流程发起人的id
                    String uid = getInitiator(taskId);
                    User user = new User();
                    user.setId(uid);
                    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
                    queryWrapper.eq("id",uid);
                    resultMap.put("userInfo",  userDao.selectList(queryWrapper));
                }
                break;
            default:
                break;
        }
        return resultMap;
    }

    @Override
    public ArrayList<Map<String, Object>> getProcessList(String taskId) {
        // 创建结果集对象
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        // 判断任务id是否为空
        if (StringUtils.isEmpty(taskId)) {
            System.out.println("taskId为空");
        }else{
            // 查询流程历史
            List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(taskId).orderByTaskCreateTime().asc().list();
            // 判断是否存在流程历史记录
            if (historicTaskInstanceList != null && historicTaskInstanceList.size() > 0) {
                for (HistoricTaskInstance history : historicTaskInstanceList) {
                    HashMap<String, Object> map = new HashMap<>();
                    // 获取任务
                    List<Comment> comment = taskService.getTaskComments(history.getId(), "comment");
                    String msg = "";
                    // 获取审核人code
                    String assignee = history.getAssignee();
                    if (StringUtils.isEmpty(assignee)) {
                        continue;
                    }
                    if (comment.size() > 0) {
                        Comment com = comment.get(0);
                        msg = com.getFullMessage();
                    }
                    map.put("flowMsg", msg);
                    //根据id查name
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id",assignee);
                    map.put("assignee", userDao.selectOne(queryWrapper).getUsername());
                    map.put("flowName", history.getName());
                    if (history.getEndTime()!=null) {
                        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(history.getEndTime());
                        map.put("endTime", format);
                        if (history.getDescription()!=null) {
                            map.put("flowState", history.getDescription());
                        } else {
                            map.put("flowState", "已处理");
                        }
                    } else {
                        map.put("endTime", "");
                        map.put("flowState", "处理中");
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }

    @Override
    public int taskSubmit(String taskId, String type, String assignee) {
        try {
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(taskId).singleResult();
            // 判断流程是否存在
            if (task == null) {
                log.info("没有查询到该任务");
                return 0;
            }else {
                // 获取流程
                String processInstanceId = task.getProcessInstanceId();
                //判断是退回还是通过按钮
                Demand demand = new Demand();
                QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("taskid", taskId);

                Map<String, Object> vars = new HashMap<>();
                log.info("task.getTaskDefinitionKey()：{}", task.getTaskDefinitionKey());

                //审批动作
                switch (task.getTaskDefinitionKey()) {
                    case "writeform": //填写表单
                        log.info("填写表单");
                        //设置下一节点审批人
                        vars.put("assignee1", assignee);//流程参数 assignee1
                        demand.setStatus("1");
                        taskService.addComment(task.getId(), processInstanceId, "comment", "发起流程");
                        break;
                    case "shenhe1": //组长审批
                        log.info("组长审批");
                        if (type.equals("0")) {//退回
                            demand.setStatus("0");
                            demandDao.update(demand, queryWrapper);
                            //设置初始节点审批人
                            vars.put("assignee0", getInitiator(taskId));
                            //设置传给第一个网关的值是0
                            vars.put("accept1", "0");
                            taskService.addComment(task.getId(), processInstanceId, "comment", "退回");
                        } else {//通过
                            //设置传给第一个网关的值是1
                            vars.put("accept1", "1");
                            //设置下一节点审批人
                            vars.put("assignee2", assignee);
                            // 修改为开发经理审批
                            demand.setStatus("2");
                            taskService.addComment(task.getId(), processInstanceId, "comment", "通过");
                        }
                        break;
                    case "shenhe2": //开发经理审批
                        log.info("开发经理审批");
                        if (type.equals("0")) {//退回
                            demandDao.deletevarinst(processInstanceId);
                            demand.setStatus("0");
                            demandDao.update(demand, queryWrapper);
                            //设置初始节点审批人
                            vars.put("assignee0", getInitiator(taskId));
                            //设置传给第二个网关的值是0
                            vars.put("accept2", "0");
                            taskService.addComment(task.getId(), processInstanceId, "comment", "退回");
                        } else {//通过
                            //设置传给第二个网关的值是1
                            vars.put("accept2", "1");
                            //设置下一节点审批人
                            vars.put("assignee3", assignee);
                            // 修改为项目经理审批
                            demand.setStatus("3");
                            taskService.addComment(task.getId(), processInstanceId, "comment", "通过");
                        }
                        break;
                    case "shenhe3": //项目经理审批
                        log.info("项目经理审批");
                        // 修改为结束审批
                        demand.setStatus("4");
                        taskService.addComment(task.getId(), processInstanceId, "comment", "结束流程");
                        break;
                    default:
                        break;
                }
                // 处理流程
                taskService.complete(task.getId(), vars, true);

                // 处理业务表的状态
                return demandDao.update(demand, queryWrapper);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取发起人
     * @param taskId 任务id
     * @return
     */
    protected String getInitiator(String taskId) {
        QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("taskid",taskId);
        return demandDao.selectOne(queryWrapper).getCreateuser();
    }


    /**
     * 流程图
     * @param processInstanceId
     * @return
     */
    @Override
    public InputStream getProcessDiagram(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        // null check
        if (processInstance != null) {
            // get process model
            BpmnModel model = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

            if (model != null && model.getLocationMap().size() > 0) {
                ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
                // 生成流程图 已启动的task 高亮
                return generator.generateDiagram(
                        model,
                        "png",
                        runtimeService.getActiveActivityIds(processInstanceId),
                        new ArrayList<String>(),
                        "宋体",
                        "宋体",
                        null,
                        1.0
                );
                // 生成流程图 都不高亮
//                return generator.generateDiagram(model, Collections.<String>emptyList());
            }
        }
        return null;
    }

    @Override
    public InputStream getHistoryProcessDiagram(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(processInstanceId)
                .singleResult();

        if(historicProcessInstance.getProcessDefinitionId() != null) {
            //获取BPMN模型对象
            BpmnModel model = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            if (model != null && model.getLocationMap().size() > 0) {
                //获取历史活动
                List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery()
                        .processDefinitionId(historicProcessInstance.getProcessDefinitionId()).list();

                //获取流程实例当前的节点，需要高亮显示
//                List<String> currentActs = Collections.EMPTY_LIST; //非高亮
                //最后一个高亮
                List<String> currentActs = highLightedActivitList.stream()
                        .filter(h->"endEvent".equals(h.getActivityType()))
                        .map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());
                ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
                return generator.generateDiagram(
                        model,
                        "png",
                        currentActs.subList(0,1),
                        new ArrayList<String>(),
                        "宋体",
                        "宋体",
                        null,
                        1.0
                );
            }
        }

        return null;
    }

    @Override
    public List<Map<String, String>> toDoTaskList(String uid) {
        List<Map<String, String>> list = new ArrayList<>();
        //根据当前用户查询
        List<Task> taskAllList = taskService.createTaskQuery().taskAssignee(uid).list();//当前登录 审核代办
        for (Task task : taskAllList) {
            Map map = new HashMap();
//            map.put("taskId",task.getId());
            map.put("taskName",task.getName());
            map.put("processName",task.getProcessDefinitionId());
            //根据taskId再查找业务内容
            QueryWrapper<Demand> queryWrapper = new QueryWrapper<>();
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String taskId = historicProcessInstance.getBusinessKey();
            queryWrapper.eq("taskid",taskId);
            Demand demand = demandDao.selectOne(queryWrapper);
            map.put("taskId",demand.getTaskid());
            map.put("id",demand.getId());
            map.put("content",demand.getContent());
            map.put("name",demand.getName());
            map.put("status",demand.getStatus());
            list.add(map);
        }
        return list;
    }
}
