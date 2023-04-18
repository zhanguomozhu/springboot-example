package com.dyq.springboot.jdbc.controller;

import com.dyq.springboot.jdbc.dao.ArticleJDBCDAO;
import com.dyq.springboot.jdbc.entity.Article;
import com.dyq.springboot.jdbc.service.ArticleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 文章(Article)表控制层
 *
 * @author makejava
 * @since 2023-04-10 15:45:47
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<Article> queryByPage(@PathVariable Long id) {
        return ResponseEntity.ok(this.articleService.getArticle(id));
    }



    //多数据源
    @Resource
    private JdbcTemplate primaryJdbcTemplate;
    @Resource
    private JdbcTemplate secondaryJdbcTemplate;

    @Resource
    private ArticleJDBCDAO articleJDBCDAO;


    @GetMapping("/testJdbc")
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

    @GetMapping("/testJdbcTx")
    public void testJdbcTx() {
        this.articleService.saveArticle( Article.builder()
                .author("zimug").title("primaryJdbcTemplate").content("ceshi").createTime(new Date()).build());
    }

}

