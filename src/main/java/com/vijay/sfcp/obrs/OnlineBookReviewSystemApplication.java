package com.vijay.sfcp.obrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBookReviewSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookReviewSystemApplication.class, args);
    }

    /*@Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**","/static/images/**")
                    .addResourceLocations("classpath:/static/images/books/","/WEB-INF/static/images/")
                    .setCachePeriod(0);
        }
    }*/
/*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**","/static/images/**", "/images/**")
                .addResourceLocations("classpath:/images/","/WEB-INF/static/images/", "/WEB-INF/images/")
                .setCachePeriod(0);
    }*/
}

