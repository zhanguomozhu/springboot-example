package com.dyq.springboot.sentinel.service;

import com.dyq.springboot.sentinel.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 文章(Article)表服务接口
 *
 * @author makejava
 * @since 2023-04-12 19:38:11
 */
public interface ArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Article queryById(Integer id);

    /**
     * 分页查询
     *
     * @param article     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Article> queryByPage(Article article, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    Article insert(Article article);

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    Article update(Article article);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
