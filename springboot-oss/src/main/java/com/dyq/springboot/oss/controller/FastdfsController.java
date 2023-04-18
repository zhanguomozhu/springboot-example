package com.dyq.springboot.oss.controller;

import com.dyq.springboot.oss.common.CommonResult;
import com.dyq.springboot.oss.utils.FastDfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/fastdfs")
public class FastdfsController {

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @PostMapping("/upload")
    public CommonResult upload(MultipartFile[] files) {
        CommonResult commonResult;
        try {
            List<String> names = new ArrayList<>(files.length);
            for (MultipartFile file: files) {
                String fileId = fastDfsUtil.uploadFile(file,file.getOriginalFilename());
                names.add(fastDfsUtil.getSourceUrl(fileId));
            }
            commonResult = CommonResult.success(names);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed("上传失败");
        }
        return commonResult;
    }

}

