package com.vijay.sfcp.obrs;

import com.vijay.sfcp.obrs.common.dto.StorageProperties;
import com.vijay.sfcp.obrs.common.service.StorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class OnlineBookReviewSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookReviewSystemApplication.class, args);
    }
}

