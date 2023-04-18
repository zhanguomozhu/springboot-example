package com.dyq.springboot.restemplate.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Configuration
public class ContextConfig {

    //默认使用JDK 自带的HttpURLConnection作为底层实现
//    @Bean
//    public RestTemplate restTemplate(){
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate;
//    }


    /**
     * 切换底层客户端为okhttp
     * @return
     */
//    @Bean("OKHttp3")
//    public RestTemplate OKHttp3RestTemplate(){
//        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
//        return restTemplate;
//    }


    /**
     * 切换底层客户端为httpClient
     * @return
     */
//    @Bean("httpClient")
//    public RestTemplate httpClientRestTemplate(){
//        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//        return restTemplate;
//    }


    @Bean("OKHttp3")
    public RestTemplate OKHttp3RestTemplate(){
        //方式1
//        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
//        //添加拦截器
//        restTemplate.getInterceptors().add(getCustomInterceptor());

        //方式2
//        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
//        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin","1234"));

        //方式3
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("admin","1234").build();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
        return restTemplate;
    }


    //实现一个拦截器：使用拦截器为每一个HTTP请求添加Basic Auth认证用户名密码信息
    private ClientHttpRequestInterceptor getCustomInterceptor(){
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("authorization",
                    "Basic " +
                            Base64.getEncoder()
                                    .encodeToString("admin:adminpwd".getBytes()));
            return execution.execute(httpRequest, bytes);
        };
        return interceptor;
    }

    //这段代码是《第3节-底层HTTP客户端实现切换》的内容
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 100000;
        OkHttp3ClientHttpRequestFactory clientHttpRequestFactory
                = new OkHttp3ClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

}