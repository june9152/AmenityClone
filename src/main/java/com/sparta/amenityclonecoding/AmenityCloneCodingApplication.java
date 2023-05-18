package com.sparta.amenityclonecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AmenityCloneCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmenityCloneCodingApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedOrigins("http://localhost:8080", "http://localhost:3000", "http://127.0.0.1:3000",
                                        "http://hanghae99-14-clone3.s3-website.ap-northeast-2.amazonaws.com",
                                        "http://database-1.cnxatxk35zhr.ap-northeast-2.rds.amazonaws.com")
//                        .allowedOriginPatterns("*")
                        .exposedHeaders("ACCESS_KEY", "REFRESH_KEY", "Authorization", "USER_ROLE", "USER_EMAIL")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                        //.allowedHeaders()
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

}
