package com.dyq.springboot.redis.service.impl;


import com.dyq.springboot.redis.dao.PersonDao;
import com.dyq.springboot.redis.entity.Person;
import com.dyq.springboot.redis.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * (Person)表服务实现类
 *
 * @author makejava
 * @since 2023-04-01 12:24:53
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Resource
    private PersonDao personDao;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Person queryById(Integer id) {
        // 从缓存中获取城市信息
        String key = "person_" + id;
        ValueOperations<String, Person> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Person person = operations.get(key);

            LOGGER.info("PersonServiceImpl.queryById() : 从缓存中获取了人员 >> " + person.getName());
            return person;
        }

        // 从 DB 中获取城市信息
        Person person = personDao.queryById(id);

        // 插入缓存
        operations.set(key, person, 100, TimeUnit.SECONDS);
        LOGGER.info("PersonServiceImpl.queryById() : 人员插入缓存 >> " + person.getName());

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
