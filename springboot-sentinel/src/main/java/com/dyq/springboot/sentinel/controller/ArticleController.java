package com.dyq.springboot.sentinel.controller;

import com.dyq.springboot.sentinel.common.CommonResult;
import com.dyq.springboot.sentinel.entity.Article;
import com.dyq.springboot.sentinel.service.ArticleService;
import com.dyq.springboot.sentinel.vo.ArticleVO;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文章(Article)表控制层
 *
 * @author makejava
 * @since 2023-04-12 19:38:08
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    private Mapper dozerMapper = DozerBeanMapperBuilder.buildDefault();;

    public static final String CACHE_OBJECT = "article";  //缓存名称

    //获取
//    @GetMapping("{id}")
//    @Cacheable(value = CACHE_OBJECT,key = "#id")   //这里的value和key参考下面的redis数据库截图理解
//    public ArticleVO getArticle(@PathVariable Integer id) {
//        return dozerMapper.map(articleService.queryById(id),ArticleVO.class);
//    }


//    public static final String CACHE_LIST_KEY  = "\"list\"";
//    // 列表
//    @Cacheable(value = CACHE_OBJECT,key = CACHE_LIST_KEY)
//    public List<ArticleVO> getAll() {
//        List<Article> articles = articleMapper.selectList(null);
//        return DozerUtils.mapList(articles,ArticleVO.class);
//    }


//    //删除后，删除缓存
//    @Override
//    @Caching(evict = {
//            @CacheEvict(value = CACHE_OBJECT,key = CACHE_LIST_KEY),   //删除List集合缓存
//            @CacheEvict(value = CACHE_OBJECT,key = "#id")  //删除单条记录缓存
//    })
//    public void deleteArticle(Long id) {
//        articleMapper.deleteById(id);
//    }

//    //新增后删除缓存
//    @CacheEvict(value = CACHE_OBJECT,key = CACHE_LIST_KEY)   //删除List集合缓存
//    public void saveArticle(ArticleVO article) {
//        Article articlePO = dozerMapper.map(article, Article.class);
//        articleMapper.insert(articlePO);
//    }

//    //更新后，插入新的缓存，删除list缓存
//    @CachePut(value = CACHE_OBJECT,key = "#article.getId()")
//    @CacheEvict(value = CACHE_OBJECT,key = CACHE_LIST_KEY)
//    public ArticleVO updateArticle(ArticleVO article) {
//        Article articlePO = dozerMapper.map(article,Article.class);
//        articleMapper.updateById(articlePO);
//        return article;  //为了保证一致性，最后返回的更新结果，最好从数据库去查
//    }


//    //更新后，删除list，删除单条缓存
//    @Override
//    @Caching(evict = {
//            @CacheEvict(value = CACHE_OBJECT,key = CACHE_LIST_KEY),   //删除List集合缓存
//            @CacheEvict(value = CACHE_OBJECT,key = "#article.getId()")  //删除单条记录缓存
//    })
//    public void updateArticle(ArticleVO article) {
//        Article articlePO = dozerMapper.map(article,Article.class);
//        articleMapper.updateById(articlePO);
//    }


    /**
     * 分页查询
     *
     * @param article     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public CommonResult<Page<Article>> queryByPage(Article article, PageRequest pageRequest) {
        return CommonResult.success(this.articleService.queryByPage(article, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @Cacheable(value="article")
    public CommonResult<Article> queryById(@PathVariable("id") Integer id) {
        return CommonResult.success(this.articleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param article 实体
     * @return 新增结果
     */
    @PostMapping
    public CommonResult<Article> add(Article article) {
        return CommonResult.success(this.articleService.insert(article));
    }

    /**
     * 编辑数据
     *
     * @param article 实体
     * @return 编辑结果
     */
    @PutMapping
    public CommonResult<Article> edit(Article article) {
        return CommonResult.success(this.articleService.update(article));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public CommonResult<Boolean> deleteById(Integer id) {
        return CommonResult.success(this.articleService.deleteById(id));
    }

}

