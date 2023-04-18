package com.dyq.springboot.jdbc.service.impl;

import com.dyq.springboot.jdbc.dao.ArticleJDBCDAO;
import com.dyq.springboot.jdbc.entity.Article;
import com.dyq.springboot.jdbc.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service   //服务层依赖注入注解
public class ArticlleJDBCService  implements ArticleService {

    @Resource
    private
    ArticleJDBCDAO articleJDBCDAO;

    //多数据源
    @Resource
    private JdbcTemplate primaryJdbcTemplate;
    @Resource
    private JdbcTemplate secondaryJdbcTemplate;

    @Transactional
    public Article saveArticle( Article article) {
//        articleJDBCDAO.save(article);


        articleJDBCDAO.save(article,primaryJdbcTemplate);
        articleJDBCDAO.save(article,secondaryJdbcTemplate);
        int a = 2/0;  //人为制造一个异常，用于测试事务
        return article;
    }

    public void deleteArticle(Long id){
        articleJDBCDAO.deleteById(id);
    }

    public void updateArticle(Article article){
        articleJDBCDAO.updateById(article);
    }

    public Article getArticle(Long id){
        return articleJDBCDAO.findById(id);
    }

    public List<Article> getAll(){
        return articleJDBCDAO.findAll();
    }
}
