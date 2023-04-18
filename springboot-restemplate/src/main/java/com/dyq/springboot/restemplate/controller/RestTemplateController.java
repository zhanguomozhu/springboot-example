package com.dyq.springboot.restemplate.controller;


import com.dyq.springboot.restemplate.config.MyRestErrorHandler;
import com.dyq.springboot.restemplate.pojo.PostDTO;
import com.dyq.springboot.restemplate.service.RetryService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/test1")
    public void test1(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }

    /**
     * 切换底层客户端为okhttp
     * @return
     */
    @GetMapping("/okhttp")
    public void okhttp(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }


    /**
     * 切换底层客户端为httpclient
     * @return
     */
    @GetMapping("/httpclient")
    public void httpclient(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }



    /**
     * 切换底层客户端为httpclient
     * @return
     */
    @GetMapping("/testGet")
    public void testGet(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }


    /**
     * get
     * @return
     */
    @GetMapping("/get")
    public void getString(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }

    /**
     * testPoJO
     * @return
     */
    @GetMapping("/testPoJO")
    public void testPoJO() {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        PostDTO postDTO = restTemplate.getForObject(url, PostDTO.class);
        System.out.println(postDTO.toString());
    }

    /**
     * testArrays
     * @return
     */
    @GetMapping("/testArrays")
    public void testArrays() {
        //占位符1
//        String url = "http://jsonplaceholder.typicode.com/{1}/{2}";
//        PostDTO postDTO = restTemplate.getForObject(url, PostDTO.class, "posts", 1);

        //占位符2
//        String url = "http://jsonplaceholder.typicode.com/{type}/{id}";
//        String type = "posts";
//        int id = 1;
//        PostDTO postDTO = restTemplate.getForObject(url, PostDTO.class, type, id);

        //占位符3
//        String url = "http://jsonplaceholder.typicode.com/{type}/{id}";
//        Map<String,Object> map = new HashMap<>();
//        map.put("type", "posts");
//        map.put("id", 1);
//        PostDTO  postDTO = restTemplate.getForObject(url, PostDTO.class, map);


        String url = "http://jsonplaceholder.typicode.com/posts";
        PostDTO[] postDTOs = restTemplate.getForObject(url, PostDTO[].class);
        System.out.println("数组长度：" + postDTOs.length);
    }

    /**
     * testPost
     * @return
     */
    @GetMapping("/testPost")
    public void testSimple()  {
        // 请求地址
        String url = "http://jsonplaceholder.typicode.com/posts";

        // 要发送的数据对象
        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(110);
        postDTO.setTitle("zimug 发布文章");
        postDTO.setBody("zimug 发布文章 测试内容");

        // 发送post请求，并输出结果
        PostDTO result = restTemplate.postForObject(url, postDTO, PostDTO.class);
        System.out.println(result);
    }

    /**
     * testForm
     * @return
     */
    @GetMapping("/testForm")
    public void testForm() {
//占位符
//        String url = "http://jsonplaceholder.typicode.com/{1}/{2}";
//        String url = "http://jsonplaceholder.typicode.com/{type}/{id}";
        // 请求地址
        String url = "http://jsonplaceholder.typicode.com/posts";

        // 请求头设置,x-www-form-urlencoded格式的数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("title", "zimug 发布文章第二篇");
        map.add("body", "zimug 发布文章第二篇 测试内容");

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);

        // 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);
    }

    /**
     * testEntityPoJo
     * @return
     */
    @GetMapping("/testEntityPoJo")
    public void testEntityPoJo() {
        // 请求地址
        String url = "http://jsonplaceholder.typicode.com/posts";

        // 要发送的数据对象
        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(110);
        postDTO.setTitle("zimug 发布文章");
        postDTO.setBody("zimug 发布文章 测试内容");

        // 发送post请求，并输出结果
        ResponseEntity<String> responseEntity
                = restTemplate.postForEntity(url, postDTO, String.class);
        String body = responseEntity.getBody(); // 获取响应体
        System.out.println("HTTP 响应body：" + postDTO.toString());


        //以下是postForEntity比postForObject多出来的内容
        HttpStatus statusCode = responseEntity.getStatusCode(); // 获取响应码
        int statusCodeValue = responseEntity.getStatusCodeValue(); // 获取响应码值
        HttpHeaders headers = responseEntity.getHeaders(); // 获取响应头


        System.out.println("HTTP 响应状态：" + statusCode);
        System.out.println("HTTP 响应状态码：" + statusCodeValue);
        System.out.println("HTTP Headers信息：" + headers);
    }

    /**
     * testURI
     * @return
     */
    @GetMapping("/testURI")
    public void testURI() {
        // 请求地址
        String url = "http://jsonplaceholder.typicode.com/posts";

        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(110);
        postDTO.setTitle("zimug 发布文章");
        postDTO.setBody("zimug 发布文章 测试内容");

        // 发送post请求，并输出结果
        URI uri = restTemplate.postForLocation(url,postDTO);
        System.out.println(uri);
    }

    /**
     * RESTful风格
     * testDelete
     * @return
     */
    @GetMapping("/testDelete")
    public void testDelete()  {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        restTemplate.delete(url);
    }

    /**
     * RESTful风格
     * testPut
     * @return
     */
    @GetMapping("/testPut")
    public void testPut()  {
        // 请求地址
        String url = "http://jsonplaceholder.typicode.com/posts/1";

        // 要发送的数据对象（修改数据）
        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(110);
        postDTO.setTitle("zimug 发布文章");
        postDTO.setBody("zimug 发布文章 测试内容");

        // 发送PUT请求
        restTemplate.put(url, postDTO);
    }

    /**
     * testExchange
     * @return
     */
    @GetMapping("/testExchange")
    public void testExchange()  {
        // 请求地址
        String url = "http://jsonplaceholder.typicode.com/posts/1";

//        //使用getForEntity发送GET请求
//        ResponseEntity<PostDTO> responseEntity
//                = restTemplate.getForEntity(url, PostDTO.class);
        //使用exchange发送GET请求
//        ResponseEntity<PostDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
//                null, PostDTO.class);
//
//
//        // 使用postForEntity发送POST请求
//        ResponseEntity<String> responseEntity
//                = restTemplate.postForEntity(url, postDTO, String.class);
//        // 使用exchange发送POST请求
//        ResponseEntity<String> responseEntity
//                = restTemplate.exchange(url, HttpMethod.POST,null, String.class);
//
//
//        // 使用delete发送DELETE请求，返回值为void
//        restTemplate.delete(url);
        // 使用exchange发送DELETE请求
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.DELETE,null,String.class);
    }


    /**
     * testHEAD
     * @return
     */
    @GetMapping("/testHEAD")
    public void testHEAD()  {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        HttpHeaders httpHeaders  = restTemplate.headForHeaders(url);

//        //断言该资源接口数据为JSON类型
//        ssertTrue(httpHeaders.getContentType()
//                .includes(MediaType.APPLICATION_JSON));
        System.out.println(httpHeaders);
    }

    /**
     * testOPTIONS
     * @return
     */
    @GetMapping("/testOPTIONS")
    public void testOPTIONS()  {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        Set<HttpMethod> optionsForAllow  = restTemplate.optionsForAllow(url);

        HttpMethod[] supportedMethods
                = {HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
        //测试该url资源是否支持GET、POST、PUT、DELETE，即增删改查
//        assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
    }



    /**
     * testUpload
     * @return
     */
    @GetMapping("/testUpload")
    public void testUpload()  {
        // 文件上传服务上传接口
        String url = "http://localhost:8888/upload";
        // 待上传的文件（存在客户端本地磁盘）
        String filePath = "D:\\data\\local\\splash.png";

        // 封装请求参数
        FileSystemResource resource = new FileSystemResource(new File(filePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("uploadFile", resource);  //服务端MultipartFile uploadFile
        //param.add("param1", "test");   //服务端如果接受额外参数，可以传递


        // 发送请求并输出结果
        System.out.println("--- 开始上传文件 ---");
        String result = restTemplate.postForObject(url, param, String.class);
        System.out.println("--- 访问地址：" + result);
    }


    /**
     * testDownLoad
     * @return
     */
    @GetMapping("/testDownLoad")
    public void testDownLoad() throws IOException {
        // 待下载的文件地址
        String url = "http://localhost:8888/2020/08/12/028b38f1-3f9b-4088-9bea-1af8c18cd619.png";
        ResponseEntity<byte[]> rsp = restTemplate.getForEntity(url, byte[].class);
        System.out.println("文件下载请求结果状态码：" + rsp.getStatusCode());

        // 将下载下来的文件内容保存到本地
        String targetPath = "D:\\data\\local\\splash-down.png";
        Files.write(Paths.get(targetPath), Objects.requireNonNull(rsp.getBody(),
                "未获取到下载文件"));
    }


    /**
     * testDownLoadBigFile
     * @return
     */
    @GetMapping("/testDownLoadBigFile")
    public void testDownLoadBigFile() throws IOException {
        // 待下载的文件地址
        String url = "http://localhost:8888/2020/08/12/028b38f1-3f9b-4088-9bea-1af8c18cd619.png";
        // 文件保存的本地路径
        String targetPath = "D:\\data\\local\\splash-down-big.png";
        //定义请求头的接收类型
        RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        //对响应进行流式处理而不是将其全部加载到内存中
        restTemplate.execute(url, HttpMethod.GET, requestCallback, clientHttpResponse -> {
            Files.copy(clientHttpResponse.getBody(), Paths.get(targetPath));
            return null;
        });
    }

    /**
     * testEntity
     * @return
     */
    @GetMapping("/testEntity")
    public void testEntity() {
        String url = "http://jsonplaceholder.typicode.com/postss/1";
        ResponseEntity<String> responseEntity
                = restTemplate.getForEntity(url, String.class);  //这行抛出异常
        //下面两行代码执行不到
        HttpStatus statusCode = responseEntity.getStatusCode(); // 获取响应码
        System.out.println("HTTP 响应状态：" + statusCode);
    }

    /**
     * 切换底层客户端为okhttp
     * @return
     */
    @GetMapping("/okhttp1")
    public void okhttp1(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        String str = restTemplate.getForObject(url, String.class);
        restTemplate.setErrorHandler(new MyRestErrorHandler());
        System.out.println(str);
    }

    @Resource
    RetryService retryService;

    @GetMapping("/retry")
    public HttpStatus test() {
        return retryService.testEntity();
    }

    /**
     * BasicAuth
     */
    @GetMapping("/testBasicAuth")
    public void testBasicAuth() {
        //该url上携带用户名密码是httpbin网站测试接口的要求，
        //真实的业务是不需要在url上体现basic auth用户名密码的
        String url = "http://www.httpbin.org/basic-auth/admin/adminpwd";

        //在请求头信息中携带Basic认证信息(这里才是实际Basic认证传递用户名密码的方式)
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization",
                "Basic " +
                        Base64.getEncoder()
                                .encodeToString("admin:adminpwd".getBytes()));

        //发送请求
        HttpEntity<String> ans = restTemplate
                .exchange(url,
                        HttpMethod.GET,   //GET请求
                        new HttpEntity<>(null, headers),   //加入headers
                        String.class);  //body响应数据接收类型
        System.out.println(ans);
    }

    /**
     * 使用代理
     */
    @GetMapping("/testProxyIp")
    public void testProxyIp() {

        String url = "http://www.httpbin.org/ip";

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(
                new Proxy(
                        Proxy.Type.HTTP,
                        new InetSocketAddress("88.99.10.251", 1080)  //设置代理服务
                )
        );
        restTemplate.setRequestFactory(requestFactory);
        //发送请求
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);  //打印响应结果
    }
}
