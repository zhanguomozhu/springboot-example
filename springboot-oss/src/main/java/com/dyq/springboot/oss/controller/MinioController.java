package com.dyq.springboot.oss.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyq.springboot.oss.common.CommonResult;
import com.dyq.springboot.oss.config.MinioProp;
import com.dyq.springboot.oss.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioUtil minIoUtil;

    @PostMapping("/upload")
    public CommonResult upload(MultipartFile[] file) {
        CommonResult commonResult;
        try {
            List<String> upload = minIoUtil.upload(file);
            commonResult = CommonResult.success(JSONObject.toJSONString(upload));
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed("上传失败");
        }
        return commonResult;
    }

}

