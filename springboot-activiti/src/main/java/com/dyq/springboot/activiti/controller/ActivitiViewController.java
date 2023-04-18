package com.dyq.springboot.activiti.controller;

import com.dyq.springboot.activiti.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/activiti")
public class ActivitiViewController {

    @Resource
    private UserDao userDao;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/my_list")
    public String myList() {
        return "my_list";
    }

    @GetMapping("/daliy_list")
    public String daliyList() {
        return "daliy_list";
    }
}