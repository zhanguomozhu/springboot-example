package com.dyq.springboot.jpa.repository.db1;

import com.dyq.springboot.jpa.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findByAuthor(String author);

    //查询article表的所有数据，传入Pageable分页参数，不需要自己写SQL
    Page<Article> findAll(Pageable pageable);
    //根据author字段查询article表数据，传入Pageable分页参数，不需要自己写SQL
    Page<Article> findByAuthor(String author, Pageable pageable);
    //根据author字段和title字段，查询article表数据，传入Pageable分页参数，不需要自己写SQL
    Slice<Article> findByAuthorAndTitle(String author, String title, Pageable pageable);
}