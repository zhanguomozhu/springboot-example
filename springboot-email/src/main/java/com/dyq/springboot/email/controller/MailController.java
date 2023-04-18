package com.dyq.springboot.email.controller;


import com.dyq.springboot.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/sendEmail")
    public boolean sendEmail(){
        //接收人
        String to = "896225433@qq.com";
        //标题
        String subject = "测试邮件";
        //正文
        String context = "测试邮件正文内容：我想给陈老老老板一键三连";
        return  mailService.sendSimpleText(to, subject, context);
    }
}
