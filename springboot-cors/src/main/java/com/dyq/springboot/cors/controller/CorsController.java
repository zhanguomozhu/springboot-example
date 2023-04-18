package com.dyq.springboot.cors.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cors")
public class CorsController {


    /**
     * 注解方式跨域
     * @return
     */
    @RequestMapping("/anni")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
    public String cors( ){
        return "cors";
    }


    /**
     * Http头信息跨域
     * @param response
     * @return
     */
    @RequestMapping("/http")
    @ResponseBody
    public String cors(HttpServletResponse response){
        //使用HttpServletResponse定义HTTP请求头，最原始的方法也是最通用的方法
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        return "cors";
    }
}
