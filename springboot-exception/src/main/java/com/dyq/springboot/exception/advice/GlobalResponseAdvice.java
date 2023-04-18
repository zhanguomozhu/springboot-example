package com.dyq.springboot.exception.advice;

import com.dyq.springboot.exception.exception.AjaxResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Component
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //return returnType.hasMethodAnnotation(ResponseBody.class);
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        //如果响应结果是JSON数据类型
        if(selectedContentType.equalsTypeAndSubtype(
                MediaType.APPLICATION_JSON)){
                //为HTTP响应结果设置状态码，状态码就是AjaxResponse的code，二者达到统一
                response.setStatusCode(
                        HttpStatus.valueOf(((AjaxResponse) body).getCode())
                );
                return body;
        }
        return body;
    }
}