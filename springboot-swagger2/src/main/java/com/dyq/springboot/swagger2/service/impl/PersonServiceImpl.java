package com.dyq.springboot.swagger2.service.impl;

import com.dyq.springboot.swagger2.entity.Person;
import com.dyq.springboot.swagger2.dao.PersonDao;
import com.dyq.springboot.swagger2.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Person)表服务实现类
 *
 * @author makejava
 * @since 2023-04-10 13:20:41
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonDao personDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Person queryById(Integer id) {
        return this.personDao.queryById(id);
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
        long total = this.personDao.count(person);
        return new PageImpl<>(this.personDao.queryAllByLimit(person, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param person 实例对象
     * @return 实例对象
     */
    @Override
    public Person insert(Person person) {
        this.personDao.insert(person);
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
        this.personDao.update(person);
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
        return this.personDao.deleteById(id) > 0;
    }
}
