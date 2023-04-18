package com.dyq.springboot.restemplate.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RetryService {


  @Resource
  private RestTemplate restTemplate;

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


  @Retryable(value = RestClientException.class, maxAttempts = 3,
          backoff = @Backoff(delay = 5000L,multiplier = 2))
  public HttpStatus testEntity() {
    System.out.println("发起远程API请求:" + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
    
    String url = "http://jsonplaceholder.typicode.com/postss/1";
    ResponseEntity<String> responseEntity
            = restTemplate.getForEntity(url, String.class);

    return responseEntity.getStatusCode(); // 获取响应码
  }


}