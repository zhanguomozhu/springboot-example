package com.dyq.springboot.swagger2.controller;

import com.dyq.springboot.swagger2.entity.Person;
import com.dyq.springboot.swagger2.service.PersonService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Person)表控制层
 *
 * @author makejava
 * @since 2023-04-10 13:20:40
 */
@RestController
@RequestMapping("person")
@Api(tags = "PersonController", description = "人员控制")
@Slf4j
public class PersonController {
    /**
     * 服务对象
     */
    @Resource
    private PersonService personService;


    @ApiOperation(value = "添加文章", notes = "添加新的文章", tags = "Article",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "文章标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "author", value = "文章作者", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=200,message="成功",response=ResponseEntity.class),
    })
    @PostMapping("/article")
    public @ResponseBody  ResponseEntity saveArticle(
            @RequestParam(value="title") String title,  //参数1
            @RequestParam(value="content") String content,//参数2
            @RequestParam(value="author") String author //参数3
    ) {
        log.info("标题：{}",title);
        log.info("内容：{}",content);
        log.info("作者：{}",author);
        return null;
    }

//    @ApiModel(value = "通用响应数据结构类")
//    public class AjaxResponse {
//        @ApiModelProperty(value="请求是否处理成功")
//        private boolean isok;  //请求是否处理成功
//        @ApiModelProperty(value="请求响应状态码",example="200、400、500")
//        private int code; //请求响应状态码（200、400、500）
//        @ApiModelProperty(value="请求结果描述信息")
//        private String message;  //请求结果描述信息
//        @ApiModelProperty(value="请求结果数据")
//        private Object data; //请求结果数据（通常用于查询操作）
//    }

//    @Api：用在Controller控制器类上
//            属性tags="说明该类的功能及作用"
//
//    @ApiOperation：用在Controller控制器类的请求的方法上
//            value="说明方法的用途、作用"
//    notes="方法的备注说明"
//
//    @ApiImplicitParams：用在请求的方法上，表示一组参数说明
//    @ApiImplicitParam：请求方法中参数的说明
//    name：参数名
//    value：参数的汉字说明、解释、用途
//    required：参数是否必须传，布尔类型
//    paramType：参数的类型，即参数存储位置或提交方式
//            · header --> Http的Header携带的参数的获取：@RequestHeader
//            · query --> 请求参数的获取：@RequestParam   （如上面的例子）
//            · path（用于restful接口）--> 请求参数的获取：@PathVariable
//            · body（不常用）
//            · form（不常用）
//    dataType：参数类型，默认String，其它值dataType="Integer"
//    defaultValue：参数的默认值
//
//    @ApiResponses：用在控制器的请求的方法上，对方法的响应结果进行描述
//    @ApiResponse：用于表达一个响应信息
//    code：数字，例如400
//    message：信息，例如"请求参数没填好"
//    response：响应结果封装类，如上例子中的AjaxResponse.class
//
//    @ApiModel：value=“通常用在描述@RequestBody和@ResponseBody注解修饰的接收参数或响应参数实体类”
//    @ApiModelProperty：value="实体类属性的描述"

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

