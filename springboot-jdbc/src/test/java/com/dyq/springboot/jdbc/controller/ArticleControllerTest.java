package com.dyq.springboot.jdbc.controller;

import com.dyq.springboot.jdbc.dao.ArticleJDBCDAO;
import com.dyq.springboot.jdbc.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ArticleControllerTest {



    @Resource
    private JdbcTemplate primaryJdbcTemplate;
    @Resource
    private JdbcTemplate secondaryJdbcTemplate;

    @Resource
    private ArticleJDBCDAO articleJDBCDAO;


    @Test
    public void testJdbc() {
        articleJDBCDAO.save(
                Article.builder()
                        .author("zimug").title("primaryJdbcTemplate").content("ceshi").createTime(new Date())
                        .build(),
                primaryJdbcTemplate);
        articleJDBCDAO.save(
                Article.builder()
                        .author("zimug").title("secondaryJdbcTemplate").content("ceshi").createTime(new Date())
                        .build(),
                secondaryJdbcTemplate);
    }

}