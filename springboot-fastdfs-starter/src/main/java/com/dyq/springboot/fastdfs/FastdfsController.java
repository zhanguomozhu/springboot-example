package com.dyq.springboot.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FastDFSClientUtil fastDFSClientUtil;

    @PostMapping("/upload")
    public void upload(MultipartFile[] files) {
        try {
            List<String> names = new ArrayList<>(files.length);
            for (MultipartFile file: files) {
                String originalfileName = file.getOriginalFilename();
                String fileId = fastDFSClientUtil.uploadFile(file.getBytes(),originalfileName.substring(originalfileName.lastIndexOf(".")));
                names.add(fastDFSClientUtil.getSourceUrl(fileId));
            }
            System.out.println(names.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("aaaa");
    }

}

