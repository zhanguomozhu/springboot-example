package com.dyq.springboot.jpa.service;

import com.dyq.springboot.jpa.entity.Article;
import com.dyq.springboot.jpa.vo.ArticleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ArticleRestService {

     ArticleVO saveArticle(ArticleVO article);

     void deleteArticle(Long id);

     void updateArticle(ArticleVO article);

     ArticleVO getArticle(Long id);

     List<ArticleVO> getAll();

     List<Article> findByAuthor(String author);

     //查询article表的所有数据，传入Pageable分页参数，不需要自己写SQL
     Page<Article> findAll(Pageable pageable);
     //根据author字段查询article表数据，传入Pageable分页参数，不需要自己写SQL
     Page<Article> findByAuthor(String author, Pageable pageable);
     //根据author字段和title字段，查询article表数据，传入Pageable分页参数，不需要自己写SQL
     Slice<Article> findByAuthorAndTitle(String author, String title, Pageable pageable);
}
