package com.dyq.springboot.mockito.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
//@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Resource
    private MockMvc mockMvc;


//    //mock对象
//    private static MockMvc mockMvc;
//
//    //在所有测试方法执行之前进行mock对象初始化
//    @BeforeAll
//    static void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController()).build();
//    }

    //测试方法
    @Test
    public void saveArticle() throws Exception {


//        //模拟GET请求：
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", userId));
//
//        //模拟Post请求：
//        mockMvc.perform(MockMvcRequestBuilders.post("uri", parameters));
//
//        //模拟文件上传：
//        mockMvc.perform(MockMvcRequestBuilders.multipart("uri").file("fileName", "file".getBytes("UTF-8")));
//
//
//        //模拟session和cookie：
//        mockMvc.perform(MockMvcRequestBuilders.get("uri").sessionAttr("name", "value"));
//        mockMvc.perform(MockMvcRequestBuilders.get("uri").cookie(new Cookie("name", "value")));
//
//        //设置HTTP Header：
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("uri", parameters)
//                .contentType("application/x-www-form-urlencoded")
//                .accept("application/json")
//                .header("", ""));


        String article = "{\n" +
                "    \"id\": 11,\n" +
                "    \"name\": \"aaaaaaaaaaaa\",\n" +
                "    \"sex\": \"18\",\n" +
                "}";


//        ObjectMapper objectMapper = new ObjectMapper();
//        Article articleObj = objectMapper.readValue(article,Article.class);
//
//        //打桩模拟未实现接口
//        when(articleService.saveArticle(articleObj)).thenReturn("ok");


        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/person")
                        .contentType("application/json")
//                        .contentType("application/x-www-form-urlencoded")
//                        .contentType("multipart/form-data")
                        .content(article)
        )
                .andExpect(MockMvcResultMatchers.status().is(400))  //HTTP:status 200
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("aaaaaaaaaaaa"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.datasex").value(18))
                .andDo(print())
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());

    }

    private ResultHandler print() {
         return new ResultHandler() {
             @Override
             public void handle(MvcResult result) throws Exception {
                 System.out.println("11111");
                 System.out.println(result.getResponse().getContentAsString());
                 System.out.println("22222");
             }
         };
    }
}