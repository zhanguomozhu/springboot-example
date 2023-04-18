package com.dyq.springboot.sentinel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SessionController {


//  因为我们在同一台机器上启动多个实例，ip相同所以session是共享的。
//  如果你在不同的服务器上启动多个实例(IP)不同，你需要在应用前方加上负载均衡逆向代理才可以实现session共享
  @RequestMapping(value="/uid",method = RequestMethod.GET)
  public @ResponseBody String uid(HttpSession session) {
    return "sessionId:" + session.getId();
  }

}