package com.dyq.springboot.sentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("people")
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

  private static final long serialVersionUID = -8985545025228238754L;

  @Id
  String id;
  String firstname;
  String lastname;
  Address address;   //注意这里，不是基础数据类型

  public Person(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }
}