package com.dyq.springboot.quart.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class BaseControllerUtil {

    public static void renderResult(HttpServletResponse response, Object obj){
        PrintWriter out = null;
        try {
            String jsonArray = JSONObject.toJSONString(obj);
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            out.println(jsonArray);
            out.flush();
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}