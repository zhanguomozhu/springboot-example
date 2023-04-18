package com.dyq.springboot.restemplate.pojo;

import lombok.Data;

@Data
public class PostDTO {
    private int userId;
    private int id;
    private String title;
    private String body;
}