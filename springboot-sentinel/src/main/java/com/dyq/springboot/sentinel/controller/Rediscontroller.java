package com.dyq.springboot.sentinel.controller;

import com.dyq.springboot.sentinel.entity.Address;
import com.dyq.springboot.sentinel.entity.Person;
import com.dyq.springboot.sentinel.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class Rediscontroller {

    @Resource
    private StringRedisTemplate stringRedisTemplate;   //以String序列化方式保存数据的通用模板类

    @Resource(name = "redisTemplate")
    private ValueOperations<String,Object> valueOperations;   //以redis string类型存取Java Object(序列化反序列化)

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperations; //以redis的hash类型存储java Object

    @Resource(name = "redisTemplate")
    private ListOperations<String, Object> listOperations; //以redis的list类型存储java Object

    @Resource(name = "redisTemplate")
    private SetOperations<String, Object> setOperations;   //以redis的set类型存储java Object

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;  //以redis的zset类型存储java Object



    @Resource
    private RedisTemplate<String, Person> redisTemplate;   //默认以JDK二进制方式保存数据的通用模板类

    @GetMapping("/testString")
    public void stringRedisTemplate() {
        Person person = new Person("kobe","byrant");
        person.setAddress(new Address("洛杉矶","美国"));
        //将数据存入redis数据库
        stringRedisTemplate.opsForValue().set("player:srt","kobe byrant",20, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("player:rt",person,20, TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.opsForValue().get("player:srt"));
        System.out.println(redisTemplate.opsForValue().get("player:rt"));
    }

    @GetMapping("/testValue")
    public void testValueObj() {
        Person person = new Person("boke","byrant");
        person.setAddress(new Address("南京","中国"));
        //向redis数据库保存数据(key,value),数据有效期20秒
        valueOperations.set("player:1",person,20, TimeUnit.SECONDS); //20秒之后数据消失
        //根据key把数据取出来
        Person getBack = (Person)valueOperations.get("player:1");
        System.out.println(getBack);
    }

    @GetMapping("/testSet")
    public void testSetOperation() {
        Person person = new Person("kobe","byrant");
        Person person2 = new Person("curry","stephen");

        setOperations.add("playerset",person,person2);  //向Set中添加数据项
        //members获取Redis Set中的所有记录
        Set<Object> result = setOperations.members("playerset");
        System.out.println(result);  //包含kobe和curry的数组
    }

    @GetMapping("/testHash")
    public void HashOperations() {
        Person person = new Person("kobe","byrant");
        //使用hash的方法存储对象数据（一个属性一个属性的存，下节教大家简单的方法）
        hashOperations.put("hash:player","firstname",person.getFirstname());
        hashOperations.put("hash:player","lastname",person.getLastname());
        hashOperations.put("hash:player","address",person.getAddress());
        //取出一个对象的属性值，有没有办法一次将整个对象取出来？有，下节介绍
        String firstName = (String)hashOperations.get("hash:player","firstname");
        System.out.println(firstName);   //kobe
    }

    @GetMapping("/testList")
    public void  ListOperations() {
        //将数据对象放入队列
        listOperations.leftPush("list:player",new Person("kobe","byrant"));
        listOperations.leftPush("list:player",new Person("Jordan","Mikel"));
        listOperations.leftPush("list:player",new Person("curry","stephen"));
        //从左侧存，再从左侧取，所以取出来的数据是后放入的curry
        Person person = (Person) listOperations.leftPop("list:player");
        System.out.println(person); //curry对象
    }


    @Resource(name="redisTemplate")
    private HashOperations<String, String, Object> jacksonHashOperations;
    //注意这里的false，下文会讲解
    private HashMapper<Object, String, Object> jackson2HashMapper = new Jackson2HashMapper(false);

    @GetMapping("/testHashMap")
    public void testHashPutAll(){
        Person person = new Person("kobe","bryant");
        person.setId("kobe");
        person.setAddress(new Address("洛杉矶","美国"));

        //将对象以hash的形式放入redis数据库
        Map<String,Object> mappedHash = jackson2HashMapper.toHash(person);
        jacksonHashOperations.putAll("player:" + person.getId(), mappedHash);

        //将对象从数据库取出来
        Map<String,Object> loadedHash = jacksonHashOperations.entries("player:" + person.getId());
        Object map = jackson2HashMapper.fromHash(loadedHash);
        Person getback = new ObjectMapper().convertValue(map,Person.class);
        System.out.println(getback.toString());
    }

    @Resource
    private PersonRepository personRepository;

    @GetMapping("/testRepository")
    public void testRepository(){

        Person rand = new Person("zimug", "汉神");
        rand.setAddress(new Address("杭州", "中国"));
        personRepository.save(rand);  //存

        Optional<Person> op = personRepository.findById(rand.getId()); //取
        Person p2 = op.get();

        long count = personRepository.count();//统计Person的数量
        personRepository.delete(rand);  //删除person对象rand


        System.out.println(count);
    }

}