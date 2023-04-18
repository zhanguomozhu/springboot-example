package com.dyq.springboot.jpa.controller;

import com.dyq.springboot.jpa.repository.db2.MessageRepository;
import com.dyq.springboot.jpa.entity.Article;
import com.dyq.springboot.jpa.entity.Message;
import com.dyq.springboot.jpa.repository.db1.ArticleRepository;
import com.dyq.springboot.jpa.vo.ArticleVO;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("article")
public class TestJpaController {

    @Resource
    private ArticleRepository articleRepository;

   @GetMapping("/testUser")
    public void userTest() {
       List<Article> article = articleRepository.findByAuthor("zimug");
        System.out.println(article);
    }

    @GetMapping("/userPageTest")
    public void userPageTest() {

//        Pageable pageable = PageRequest.of(0,2);
//        //查询article表的所有数据，传入Pageable分页参数，不需要自己写SQL
//        Page<Article> articles = articleRepository.findAll(pageable);
//        System.out.println(articles.getContent());

//        //根据author字段查询article表数据，传入Pageable分页参数，不需要自己写SQL
//        Page<Article> articles = articleRepository.findByAuthor("zimug",pageable);
//        System.out.println(articles.getContent());

//        //根据author字段查询article表数据，传入Pageable分页参数,降序
//        Pageable pageable = PageRequest.of(0,2, Sort.by("createTime").descending());
//        Page<Article> articles = articleRepository.findByAuthor("zimug",pageable);
//        System.out.println(articles.getContent());

//        //根据author字段和title字段，查询article表数据，传入Pageable分页参数，不需要自己写SQL
//        Slice<Article> articles = articleRepository.findByAuthorAndTitle("zimug","primaryJdbcTemplate",pageable);
//        System.out.println(articles.getContent());
    }


    @Resource
    private MessageRepository messageRepository;
    /**
     * 多数据源测试
     */
    @GetMapping("/jpaTest")
    public void jpaTest(){
        Article article = Article.builder()
                .id(2L)
                .author("zimug")
                .content("spring boot 从青铜到王者")
                .createTime(new Date())
                //.reader(readers)
                .title("t1").build();

        Message message = Message.builder()
                .name("zimug").content("ok").build();

        //先构造一个Article对象article，这个操作针对testdb
        articleRepository.save(article);
        //在构造一个Message对象message，这个操作针对testdb2
        messageRepository.save(message);
    }

    //JTA分布式事务管理
    @Resource
    private Mapper dozerMapper;
    @Transactional
    @GetMapping("/jtaTest")
    public ArticleVO saveArticle() {
        Article article = Article.builder()
//                .id(2L)
                .author("zimug")
                .content("spring boot 从青铜到王者")
                .createTime(new Date())
                //.reader(readers)
                .title("t1").build();

        articleRepository.save(article);
        messageRepository.save(new Message(null,"zimug","爱学习"));
        int a= 2/0;
        return  dozerMapper.map(article,ArticleVO.class);
    }
}
