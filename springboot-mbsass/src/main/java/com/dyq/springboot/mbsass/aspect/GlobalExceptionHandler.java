package com.dyq.springboot.mbsass.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Map exceptionHandler(RuntimeException e) {
        Map result = new HashMap();
        result.put("code", 401);
        result.put("msg", e.getMessage());
        result.put("data", null);
        return result;
    }
}
