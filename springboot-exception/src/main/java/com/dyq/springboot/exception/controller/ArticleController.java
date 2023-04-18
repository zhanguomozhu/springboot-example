package com.dyq.springboot.exception.controller;

import com.dyq.springboot.exception.exception.AjaxResponse;
import com.dyq.springboot.exception.exception.CustomException;
import com.dyq.springboot.exception.exception.CustomExceptionType;
import com.dyq.springboot.exception.inter.ModelView;
import com.dyq.springboot.exception.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章(Article)表控制层
 *
 * @author makejava
 * @since 2023-04-11 12:33:47
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ExceptionService exceptionService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public void queryByPage(@PathVariable int id) {
        if(id==1){
            exceptionService.systemBizError();
        }else {
            exceptionService.userBizError(-1);
        }
    }

    @ModelView
    @GetMapping("/freemarker")
    public String index(Model model) {
        if(1==1){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR);
        }
        return "fremarkertemp";
    }
}

