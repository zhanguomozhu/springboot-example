package com.dyq.springboot.jpa.service.impl;

import com.dyq.springboot.jpa.entity.Article;
import com.dyq.springboot.jpa.entity.Message;
import com.dyq.springboot.jpa.repository.db1.ArticleRepository;
import com.dyq.springboot.jpa.repository.db2.MessageRepository;
import com.dyq.springboot.jpa.service.ArticleRestService;
import com.dyq.springboot.jpa.utils.DozerUtils;
import com.dyq.springboot.jpa.vo.ArticleVO;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleJPARestService implements ArticleRestService {

    //将JPA仓库对象注入
    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private Mapper dozerMapper;

    public ArticleVO saveArticle(ArticleVO article) {

        Article articlePO = dozerMapper.map(article,Article.class);
        //保存一个对象到数据库，insert
        articleRepository.save(articlePO);

        return  article;
    }

    @Override
    public void deleteArticle(Long id) {
         //根据id删除1条数据库记录
        articleRepository.deleteById(id);
    }

    @Override
    public void updateArticle(ArticleVO article) {
        Article articlePO = dozerMapper.map(article,Article.class);
        //更新一个对象到数据库，仍然使用save方法，实际是根据articlePO.id去update
        articleRepository.save(articlePO);
    }

    @Override
    public ArticleVO getArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
         //根据id查找一条数据
        return dozerMapper.map(article.get(),ArticleVO.class);
    }

    @Override
    public List<ArticleVO> getAll() {
        List<Article> articleLis = articleRepository.findAll();
        //查询article表的所有数据
        return DozerUtils.mapList(articleLis,ArticleVO.class);
    }

    @Override
    public List<Article> findByAuthor(String author) {
        return articleRepository.findByAuthor(author);
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> findByAuthor(String author, Pageable pageable) {
        return articleRepository.findByAuthor(author,pageable);
    }

    @Override
    public Slice<Article> findByAuthorAndTitle(String author, String title, Pageable pageable) {
        return articleRepository.findByAuthorAndTitle(author,title,pageable);
    }
}