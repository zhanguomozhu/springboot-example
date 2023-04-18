package com.dyq.springboot.mds.service.impl;


import com.dyq.springboot.mds.dao.master.MasterPersonDao;
import com.dyq.springboot.mds.entity.Person;
import com.dyq.springboot.mds.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Person)表服务实现类
 *
 * @author makejava
 * @since 2023-04-01 12:50:37
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Resource
    private MasterPersonDao masterPersonDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Person queryById(Integer id) {
        return this.masterPersonDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param person      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Person> queryByPage(Person person, PageRequest pageRequest) {
        long total = this.masterPersonDao.count(person);
        return new PageImpl<>(this.masterPersonDao.queryAllByLimit(person, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param person 实例对象
     * @return 实例对象
     */
    @Override
    public Person insert(Person person) {
        this.masterPersonDao.insert(person);
        return person;
    }

    /**
     * 修改数据
     *
     * @param person 实例对象
     * @return 实例对象
     */
    @Override
    public Person update(Person person) {
        this.masterPersonDao.update(person);
        return this.queryById(person.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.masterPersonDao.deleteById(id) > 0;
    }
}
