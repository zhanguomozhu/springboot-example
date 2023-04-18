package com.dyq.springboot.mbsass.entity;

import java.io.Serializable;

/**
 * (Person)实体类
 *
 * @author makejava
 * @since 2023-04-08 13:15:59
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 640171628023173431L;

    private Integer id;

    private String name;

    private Integer sex;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

}

