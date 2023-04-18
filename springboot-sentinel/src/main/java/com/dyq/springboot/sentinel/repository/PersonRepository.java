package com.dyq.springboot.sentinel.repository;

import com.dyq.springboot.sentinel.entity.Person;
import org.springframework.data.repository.CrudRepository;

//泛型第二个参数是id的数据类型
public interface PersonRepository extends CrudRepository<Person, String> {
 // 继承CrudRepository，获取基本的CRUD操作
}