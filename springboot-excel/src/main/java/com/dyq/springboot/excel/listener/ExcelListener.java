package com.dyq.springboot.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    //Excel数据
    private List<Map<Integer, Map<Integer, String>>> list;
    //Excel列名
    private Map<Integer, String> headTitleMap = new HashMap<>();

    public ExcelListener() {
        System.out.println("数据解析开始...");
        list = new ArrayList<>();
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
       // System.out.println("解析到一条数据：" + JSONObject.toJSONString(data));
        Map<Integer, Map<Integer, String>> map = new HashMap<>();
        map.put(context.readRowHolder().getRowIndex(), data);
        list.add(map);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("数据解析完成...");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("数据解析表头...");
        headTitleMap = headMap;
    }

    public List<Map<Integer, Map<Integer, String>>> getList() {
        return list;
    }

    public void setList(List<Map<Integer, Map<Integer, String>>> list) {
        this.list = list;
    }

    public Map<Integer, String> getHeadTitleMap() {
        return headTitleMap;
    }

    public void setHeadTitleMap(Map<Integer, String> headTitleMap) {
        this.headTitleMap = headTitleMap;
    }
}

