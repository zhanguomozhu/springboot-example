package com.dyq.springboot.mds.controller;



import com.dyq.springboot.mds.dao.master.MasterPersonDao;
import com.dyq.springboot.mds.dao.slave.SlavePersonDao;
import com.dyq.springboot.mds.entity.Person;
import com.dyq.springboot.mds.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Person)表控制层
 *
 * @author makejava
 * @since 2023-04-01 12:50:20
 */
@RestController
@RequestMapping("person")
public class PersonController {
    @Resource
    private MasterPersonDao masterPersonMapper;
    @Resource
    private SlavePersonDao slavePersonMapper;

    /**
     * JTA多数据源事务
     * @param
     * @return
     */
    @Transactional
    @GetMapping("/testJta")
    public void saveArticle() {
        Person person1 = new Person();
        person1.setName("aaa");
        person1.setSex(22);
        masterPersonMapper.insert(person1);

        Person person2 = new Person();
        person2.setName("bbb");
        person2.setSex(23);
        slavePersonMapper.insert(person2);

        int a = 2/0;     //认为制造被除数为0的异常

    }


    /**
     * 多数据源
     * @return
     */
    @GetMapping("/datasource")
    public String datasource() {
        Person person1 = new Person();
        person1.setName("aaa");
        person1.setSex(22);
        masterPersonMapper.insert(person1);

        Person person2 = new Person();
        person2.setName("bbb");
        person2.setSex(23);
        slavePersonMapper.insert(person2);
        return "导入成功";
    }


    /**
     * 服务对象
     */
    @Resource
    private PersonService personService;

    /**
     * 分页查询
     *
     * @param person      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Person>> queryByPage(Person person, PageRequest pageRequest) {
        return ResponseEntity.ok(this.personService.queryByPage(person, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Person> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.personService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param person 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Person> add(Person person) {
        return ResponseEntity.ok(this.personService.insert(person));
    }

    /**
     * 编辑数据
     *
     * @param person 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Person> edit(Person person) {
        return ResponseEntity.ok(this.personService.update(person));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.personService.deleteById(id));
    }

}

