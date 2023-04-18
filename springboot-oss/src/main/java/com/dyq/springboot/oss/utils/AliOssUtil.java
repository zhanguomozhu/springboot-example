package com.dyq.springboot.oss.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import com.dyq.springboot.oss.config.AliyunConfig;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description： AliOss工具类
 * @version：3.0
 */
@Component
public class AliOssUtil {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};
    @Autowired
    private OSS ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;

    /**
     * description: 判断bucket是否存在，不存在则创建
     * @param bucketName 存储bucket名称
     * @return: void
     */
    public void existBucket(String bucketName) {
        try {
            boolean exists = ossClient.doesBucketExist(bucketName);
            if (!exists) {
                ossClient.createBucket(bucketName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建存储bucket
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            ossClient.createBucket(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     * @param bucketName 存储bucket名称
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName) {
        try {
            ossClient.deleteBucket(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * description: 上传文件
     * @param multipartFile
     * @return: java.lang.String
     */
    public List<String> upload(MultipartFile[] multipartFile) {
        List<String> names = new ArrayList<>(multipartFile.length);
        for (MultipartFile file : multipartFile) {
            //文件路径
            String filePath = getFilePath(file.getOriginalFilename());

            //上传文件
            InputStream in = null;
            try {
                in = file.getInputStream();
                ossClient.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(file.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            names.add("/"+aliyunConfig.getBucketName()+"/"+filePath);
        }
        return names;
    }

    /**
     * @desc 生成路径以及文件名
     * 例如：//images/2019/04/28/15564277465972939.jpg
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * description: 下载文件
     * @param fileName
     * @return: org.springframework.http.ResponseEntity<byte [ ]>
     */
    public ResponseEntity<byte[]> download(String fileName) {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        // 设置最大个数。
        final int maxKeys = 200;
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), fileName);
            // 读取文件内容。
            in = ossObject.getObjectContent();
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            try {
                headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Arrays.asList("*"));
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }

    /**
     * 查看文件对象
     * @return 存储bucket内文件对象信息
     */
    public List<OSSObjectSummary> listObjects() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        return  objectListing.getObjectSummaries();
    }

    /**
     * 批量删除文件对象
     * @param objects 对象名称集合
     */
    public List<ObjectItem> removeObjects(List<String> objects) {
        List<ObjectItem> objectItems = new ArrayList<>();
        for (String objectName:objects) {
            ObjectItem item = new ObjectItem();
            item.setObjectName(objectName);
            try {
                ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
                item.setIsDelete(1);
            }catch (Exception e){
                e.printStackTrace();
                item.setIsDelete(0);
            }
            objectItems.add(item);
        }
        return objectItems;
    }


}

