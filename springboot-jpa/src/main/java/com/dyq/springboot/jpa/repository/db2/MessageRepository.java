package com.dyq.springboot.jpa.repository.db2;

import com.dyq.springboot.jpa.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="message")
public interface MessageRepository extends JpaRepository<Message,Long> {
}