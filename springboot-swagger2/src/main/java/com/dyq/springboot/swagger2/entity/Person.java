package com.dyq.springboot.swagger2.entity;

import java.io.Serializable;

/**
 * (Person)实体类
 *
 * @author makejava
 * @since 2023-04-10 13:20:40
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 321343100690594728L;

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

