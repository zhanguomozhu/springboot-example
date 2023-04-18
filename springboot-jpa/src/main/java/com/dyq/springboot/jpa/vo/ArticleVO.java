package com.dyq.springboot.jpa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO {

    private Long id;

    private String author;

    private String title;

    private String content;

    private Date createTime;
}