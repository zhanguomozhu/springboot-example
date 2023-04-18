package com.dyq.springboot.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 文章(Article)实体类
 *
 * @author makejava
 * @since 2023-04-10 15:48:48
 */
@Builder
public class Article implements Serializable {
    private static final long serialVersionUID = -33981375102992102L;
    
    private Integer id;
    
    private String author;
    
    private String title;
    
    private String content;
    
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

