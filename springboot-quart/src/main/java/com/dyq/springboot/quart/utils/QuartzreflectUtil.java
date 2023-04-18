package com.dyq.springboot.quart.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dyq.springboot.quart.entity.Params;
import com.dyq.springboot.quart.job.DynamicJob;
import com.dyq.springboot.quart.service.QuartzService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class QuartzreflectUtil {

    @SuppressWarnings("all")
    @Autowired
    QuartzService quartzService;

    static Logger logger = LoggerFactory.getLogger(QuartzreflectUtil.class);

    /**
     * 定时任务执行的方法，通过反射调用该类中的方法
     * @param context
     */
    @SuppressWarnings("all")
    public void reflect(JobExecutionContext context){
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String method = null;
        String cla = null;
        String jobName = null;
        String jobGroupName = null;
        Object[] finalMethodParam = new Object[0];
        try {
            cla = jobDataMap.getString("class");
            jobName = jobDataMap.get("jobName").toString();
            jobGroupName = jobDataMap.get("jobGroupName").toString();
            finalMethodParam = JSONObject.parseObject(JSON.toJSONString(jobDataMap.get("finalMethodParam")),Object[].class);
        }catch (Exception e){
            if (jobName!=null && jobGroupName!=null){
                quartzService.deleteJob(jobName, jobGroupName);
                logger.warn("定时任务方法调用异常，已停止任务   jobName："+jobName+";\tjobGroupName:"+jobGroupName);
            }else {
                logger.error("定时任务执行错误，无法停止任务，请手动停止任务");
            }

            e.printStackTrace();
        }
        DynamicJob bean = SpringContextUtil.getBean(cla, DynamicJob.class);
        bean.task(finalMethodParam);
    }

    /**
     * 调用定时任务时，需要封装成obj[]才可以使用，工具类整合数据为obj[]
     * @param list          传递过来的list对象数据
     * @param obj               一个限定长度的obj[]对象
     * @param parameterCount            参数长度
     * @return
     */
    @SuppressWarnings("all")
    public static void setObjelement(List list,Object[] obj){
        for (int i = 0; i < list.size(); i++) {                      //通过遍历，往obj2对象数组赋值
            String listClass = list.get(i).getClass().toString();           //得到list当前值的类型
            if (listClass.equals(HashMap.class.toString())) {               //判断类型是否相等。类型相等时，强转类型后复制obj
                obj[i] = (HashMap) list.get(i);
                logger.info("map赋值成功,索引位置：" + i);

            } else if (listClass.equals(String.class.toString())) {
                obj[i] = (String) list.get(i);
                logger.info("String赋值成功,索引位置：" + i);

            } else if (listClass.equals(Integer.class.toString())) {
                obj[i] = (Integer) list.get(i);
                logger.info("Integer赋值成功,索引位置：" + i);
            } else if (listClass.equals(Long.class.toString())) {
                obj[i] = (Long) list.get(i);
                logger.info("Long赋值成功,索引位置：" + i);
            }else if (listClass.equals(HttpServletRequest.class.toString())) {
                obj[i] = (HttpServletRequest) list.get(i);
                logger.info("HttpServletRequest赋值成功,索引位置：" + i);
            }else if (listClass.equals(HttpServletResponse.class.toString())) {
                obj[i] = (HttpServletResponse) list.get(i);
                logger.info("HttpServletResponse赋值成功,索引位置：" + i);
            }else if (listClass.equals(Map.class.toString())) {
                obj[i] = (Map) list.get(i);
                logger.info("Map赋值成功,索引位置：" + i);
            } else if (listClass.equals(LinkedHashMap.class.toString())) {
                obj[i] = (LinkedHashMap) list.get(i);
                logger.info("Map赋值成功,索引位置：" + i);
            }
        }
    }

    /**
     * 传入参数解析
     * @param paramSize         参数长度
     * @param param         map参数
     * @param response          响应对象
     * @param request           请求对象
     * @return          返回的需要调用定时器
     */
    @SuppressWarnings("all")
    public static List inputParamList(Integer paramSize,Map param,HttpServletResponse response,HttpServletRequest request){
        List<Object> list = new ArrayList();
        Map o;
        if (paramSize==0){
            return null;
        }
        for (int i = 1; i <= paramSize; i++) {
            o=(Map) param.get(i+"");
            if (o.containsKey("String")){
                String string = o.get("String").toString();
                list.add(string);
            }else if (o.containsKey("Map")){
                Map map = (Map) o.get("Map");
                list.add(map);
            } else if (o.containsKey("HttpServletResponse")){
                list.add(response);
            }else if (o.containsKey("HttpServletRequest")){
                list.add(request);
            }else if (o.containsKey("Long")){
                Long aLong = Long.valueOf(o.get("Long").toString());
                list.add(aLong);
            }else if (o.containsKey("Integer")){
                Integer integer = Integer.valueOf(o.get("Integer").toString());
                list.add(integer);
            }

        }
        return list;
    }

    /**
     * 传入参数解析
     * @param paramSize         参数长度
     * @param param         map参数
     * @param response          响应对象
     * @param request           请求对象
     * @return          返回的需要调用定时器
     */
    @SuppressWarnings("all")
    public static List inputParamListOptimize(Params param, HttpServletResponse response, HttpServletRequest request){
        List data = null;
        List<Object> list = new ArrayList();
        if(param.getParamData()==null || param.getParamData().size()<=0){
            return null;
        }else {
            data= param.getParamData();
        }

        for (int i = 0; i < data.size(); i++) {
            Map o = (Map) data.get(i);
            if (o.containsKey("String")){
                String string = o.get("String").toString();
                list.add(string);
            }else if (o.containsKey("Map")){
                Map map = (Map) o.get("Map");
                list.add(map);
            } else if (o.containsKey("HttpServletResponse")){
                list.add(response);
            }else if (o.containsKey("HttpServletRequest")){
                list.add(request);
            }else if (o.containsKey("Long")){
                Long aLong = Long.valueOf(o.get("Long").toString());
                list.add(aLong);
            }else if (o.containsKey("Integer")){
                Integer integer = Integer.valueOf(o.get("Integer").toString());
                list.add(integer);
            }
        }
        return list;
    }
}





