package com.dyq.springboot.oss.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyq.springboot.oss.common.CommonResult;
import com.dyq.springboot.oss.config.AliyunConfig;
import com.dyq.springboot.oss.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
/**
 * @author gaojun
 * @desc
 * @email 15037584397@163.com
 */
@Slf4j
@RestController
@RequestMapping("/alioss")
public class ALiOssController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @RequestMapping("/upload")
    @ResponseBody
    public CommonResult upload(@RequestParam("file") MultipartFile[] uploadFile){
        CommonResult commonResult;
        try {
            List<String> upload = this.aliOssUtil.upload(uploadFile);
            commonResult = CommonResult.success(JSONObject.toJSONString(upload));
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed("上传失败");
        }
        return commonResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonResult delete(@RequestParam("fileName") List<String> objectName){
        return CommonResult.success(JSONObject.toJSONString(this.aliOssUtil.removeObjects(objectName)));
    }


    @RequestMapping("/list")
    @ResponseBody
    public CommonResult list(){
        return CommonResult.success(JSONObject.toJSONString(aliOssUtil.listObjects()));
    }

    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(@RequestParam("fileName") String objectName) throws IOException {
        return this.aliOssUtil.download(objectName);
    }
}
