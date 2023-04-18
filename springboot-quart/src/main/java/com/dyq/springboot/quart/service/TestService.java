package com.dyq.springboot.quart.service;

import com.dyq.springboot.quart.job.DynamicJob;
import org.springframework.stereotype.Component;


@Component
public class TestService implements DynamicJob<String>
{

    /**
     * 定时任务执行的方法
     * @param args	可变参数，前端传递,当时无参时为null
     * @return
     */
    @Override
    public String task(Object... args)
    {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        return "1";
    }
}


