package com.dyq.springboot.sentinel.controller;

import com.dyq.springboot.sentinel.annotation.RedisLock;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;


/**
 * 分布式锁
 */
@RestController
@RequestMapping("/redisLock")
public class RedisLockController {



    @Resource
    private RedisLockRegistry redisLockRegistry;


    @GetMapping("/lock1")
    public void updateUser(String userId) {
        String lockKey = "config" + userId;
        Lock lock = redisLockRegistry.obtain(lockKey);  //获取锁资源
        try {
            lock.lock();   //加锁

            //这里写需要处理业务的业务代码
        } finally {
            lock.unlock();   //释放锁
        }
    }


    @GetMapping("/lock2")
    @RedisLock(lockKey="T('ssss').REDIS_KEY_DEAL_ACTION")
    public void save(){
        System.out.println("111111");
    }
}
