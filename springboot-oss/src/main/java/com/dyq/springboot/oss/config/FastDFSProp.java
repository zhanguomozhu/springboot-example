package com.dyq.springboot.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
//@Component
//@ConfigurationProperties(prefix = "fdf")
public class FastDFSProp {

    private Integer connect_timeout = 5;
    private Integer network_timeout = 30;
    private String charset = "UTF-8";
    private List<String> tracker_server = new ArrayList<>();
    private Integer max_total;
    private Boolean http_anti_steal_token = false;
    private String http_secret_key = "";
    private Integer http_tracker_http_port = 8987;
    private String http_server; //不是fastdfs的属性，为了方便实用自定义属性，表示访问nginx的http地址
}