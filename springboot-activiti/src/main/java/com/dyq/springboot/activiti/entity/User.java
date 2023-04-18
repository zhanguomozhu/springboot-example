package com.dyq.springboot.activiti.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-04-17 12:10:10
 */
@SuppressWarnings("serial")
public class User {
    //主键
    private String id;
    //用户名
    private String username;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

