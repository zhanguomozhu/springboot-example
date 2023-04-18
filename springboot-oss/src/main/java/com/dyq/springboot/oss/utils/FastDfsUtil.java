package com.dyq.springboot.oss.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

@Slf4j
@Component
public class FastDfsUtil {

    private static TrackerClient trackerClient = null;
    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;
    private static StorageClient1 storageClient = null;
    @Value("fast_server")
    private static String httpServer;

    @PostConstruct
    public void init(){
        try {
            ClientGlobal.init("fdfs_client.conf");
            // 初始化服务器
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            log.error("FastDfs 初始化服务器失败， 配置文件：classpath:fdfs_client.conf");
        }
    }


    /**
     * 上传文件， 按文件路径
     *
     * @param filename    : C:\Users\lx\Desktop\lmyj12.jpg
     * @param description
     * @return fastDfs生成文件路径+文件名(group1/M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg)
     * @throws Exception
     */
    public static String uploadFile(String filename, String description) throws Exception {
        if (StringUtils.isEmpty(filename)) {
            log.info("FastDfs 文件路径不能为空!");
            return null;
        }

        NameValuePair[] metaList = null;
        if (!StringUtils.isEmpty(description)) {
            metaList = new NameValuePair[]{
                    new NameValuePair("description", description)
            };
        }

        return storageClient.upload_appender_file1(filename, getFileExt(filename), metaList);
    }

    public static String uploadFile(String filename) throws Exception {
        return uploadFile(filename, null);
    }


    /**
     * 上传文件， 按表单
     *
     * @param file
     * @param description
     * @return
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file, String description) throws Exception {
        if (file == null || file.getBytes().length <= 0) {
            log.info("FastDfs 上传文件不能为空");
            return null;
        }

        NameValuePair[] metaList = null;
        if (!StringUtils.isEmpty(description)) {
            metaList = new NameValuePair[]{
                    new NameValuePair("description", description)
            };
        }
        return storageClient.upload_appender_file1(file.getBytes(), getFileExt(file), metaList);
    }

    public static String uploadFile(MultipartFile file) throws Exception {
        return uploadFile(file, null);
    }


    /**
     * 下载文件， 按浏览器默认下载目录
     *
     * @param fileUrl "group1/M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg"
     * @return
     * @throws Exception
     */
    public static byte[] downloadFile(String fileUrl) throws Exception {
        if (StringUtils.isEmpty(fileUrl)) {
            log.info("FastDfs 下载文件ID为空!");
            return null;
        }

        return storageClient.download_file1(fileUrl, 0, 0);
    }

    /**
     * 下载文件， 直接保存到指定盘+目录，不提示
     *
     * @param fileUrl       "group1/M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg"
     * @param localFilename 保存路径及文件名："D:/test.jpg"
     * @return
     * @throws Exception
     */
    public static boolean downloadFile(String fileUrl, String localFilename) throws Exception {
        if (StringUtils.isEmpty(fileUrl)) {
            log.info("FastDfs 下载文件ID为空!");
            return false;
        }

        return storageClient.download_file1(fileUrl, localFilename) == 0 ? true : false;
    }


    /**
     * 删除文件
     *
     * @param fileUrl
     * @return
     * @throws Exception
     */
    public static boolean deleteFile(String fileUrl) throws Exception {
        if (StringUtils.isEmpty(fileUrl)) {
            log.info("FastDfs 删除文件不能为空!");
            return false;
        }

        return storageClient.delete_file1(fileUrl) == 0 ? true : false;
    }

    public static boolean deleteFiles(List<String> fileUrl) throws Exception {
        if (CollectionUtils.isEmpty(fileUrl)) {
            log.info("FastDfs 删除文件不能为空!");
            return false;
        }

        int errorno = 0;
        for (String fileId : fileUrl) {
            errorno = storageClient.delete_file1(fileId);
        }
        return errorno == 0 ? true : false;
    }


    /**
     * 读文件：
     * 当启用token 时，访问就需要拼装token
     * @param filename group1/M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg
     */
    public static String getTokenFilename(String filename) throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
        int ts = (int) Instant.now().getEpochSecond();
        String auth = "FastDFS1234567890";
        String token = ProtoCommon.getToken(getM00(filename), ts, auth);
        StringBuilder sb = new StringBuilder();
        sb.append("http://static.252.com")
                .append("/" + filename)
                .append("?token=")
                .append(token)
                .append("&ts=")
                .append(ts);

        return sb.toString();
    }


    /**
     * 获取文件信息
     * @param group    group1
     * @param fileUrl  M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg
     * @return
     * @throws Exception
     */
    public static FileInfo getFileInfo(String group, String fileUrl) throws Exception {
        if (StringUtils.isEmpty(fileUrl)) {
            log.info("FastDfs 获取不能为空!");
            return null;
        }

        group = group != null ? group : "group1";

        return storageClient.get_file_info(group, fileUrl);
    }



    /**
     * --------------------------------
     * 工具方法：获取文件名，扩展名
     * --------------------------------
     */

    /**
     * 获取 m00 文件名（包含）
     * 传： group1/M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg
     * 返回：M00/00/00/wKgBw2Bb8UaEenttAAAAANC6fZI946.jpg
     * @param filename
     * @return
     */
    public static String getM00(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return null;
        }
        return filename.substring(filename.indexOf("/") + 1, filename.length());
    }



    /**
     * 获取文件后缀名（不带点）
     */
    public static String getFileExt(String filename) {
        if (StringUtils.isEmpty(filename) || !filename.contains(".")) {
            return "";
        }

        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 获取表单上传文件后缀名（不带点）
     */
    public static String getFileExt(MultipartFile file) throws IOException {
        if (file == null || file.getBytes().length <= 0) {
            return "";
        }

        String originalFileName = file.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }

    /**
     * 反盗链：获取资源的url
     * @param remoteFilename 文件地址：例如：group1/M00/00/00/wKgB2ViEMZOAeE4rAAF1DzcVmmk051.jpg(包含groupname)
     * @param group             如：group1
     */
    public String getSourceUrl(String remoteFilename, String group) throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
//        String httpserver = "http://192.168.2.110:8888/";
//        httpserver  = httpserver.endsWith("/") ? httpserver:httpserver+"/";

        int lts = (int)(System.currentTimeMillis() / 1000);
        remoteFilename = remoteFilename.replace(group+"/","");//替换掉group

        String token = ProtoCommon.getToken(remoteFilename, lts, ClientGlobal.getG_secret_key());
        return  httpServer + group + "/" + remoteFilename + "?token=" + token + "&ts=" + lts;
    }



    /**
     * 反盗链：获取资源的url
     * @param remoteFilename 文件地址：例如：group1/M00/00/00/wKgB2ViEMZOAeE4rAAF1DzcVmmk051.jpg(包含groupname)
     */
    public String getSourceUrl(String remoteFilename) throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
        if(remoteFilename != null && !remoteFilename.equals("")){
//            String httpserver = "http://192.168.2.110:8888/";
//            httpserver  = httpserver.endsWith("/") ? httpserver:httpserver+"/";
            String group = remoteFilename.substring(0,remoteFilename.indexOf("/"));

            int lts = (int)(System.currentTimeMillis() / 1000);
            remoteFilename = remoteFilename.replace(group+"/","");//替换掉group/

//            ClientGlobal.setG_charset(fastDFSProperties.getCharset());//字符集
//            ClientGlobal.setG_secret_key(fastDFSProperties.getHttp_secret_key());
            String token = ProtoCommon.getToken(remoteFilename, lts, ClientGlobal.getG_secret_key());
            return  httpServer + group + "/" + remoteFilename + "?token=" + token + "&ts=" + lts;
        }else{
            return "";
        }
    }
}
