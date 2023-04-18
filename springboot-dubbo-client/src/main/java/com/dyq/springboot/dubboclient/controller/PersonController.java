package com.dyq.springboot.dubboclient.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dyq.springboot.dubboapi.entity.Person;
import com.dyq.springboot.dubboapi.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * (Person)表控制层
 *
 * @author makejava
 * @since 2023-04-01 12:24:43
 */
@RestController
@RequestMapping("person")
public class PersonController {
    /**
     * 服务对象
     */
    @Reference(version = "1.0.0")
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
        System.out.println(id);
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

