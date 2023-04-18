package com.dyq.springboot.mybatis.service;

import com.dyq.springboot.mybatis.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Person)表服务接口
 *
 * @author makejava
 * @since 2023-04-01 12:24:52
 */
public interface PersonService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Person queryById(Integer id);

    /**
     * 分页查询
     *
     * @param person      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Person> queryByPage(Person person, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param person 实例对象
     * @return 实例对象
     */
    Person insert(Person person);

    /**
     * 修改数据
     *
     * @param person 实例对象
     * @return 实例对象
     */
    Person update(Person person);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
