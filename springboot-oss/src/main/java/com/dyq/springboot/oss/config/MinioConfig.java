package com.dyq.springboot.oss.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
public class MinioConfig {

    @Autowired
    private MinioProp minioProp;

    /**
     * 注入minio 客户端
     * @return
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioProp.getEndpoint())
                .credentials(minioProp.getAccesskey(), minioProp.getSecretKey())
                .build();
    }

}
