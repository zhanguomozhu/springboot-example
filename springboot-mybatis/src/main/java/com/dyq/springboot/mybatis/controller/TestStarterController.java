package com.dyq.springboot.mybatis.controller;


import com.dyq.springboot.starter.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test")
public class TestStarterController {


    @Resource(name = "demo")
    private DemoService demoService;

    @GetMapping("/test")
    public String test() {

        boolean valid = demoService.isValidLength("test");
        if (valid)
            return demoService.paramsInfo();
        else
            return "无效数据";
    }
}
