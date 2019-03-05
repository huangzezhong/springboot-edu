package com.gz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages = "com.gz",
        exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.activiti.spring.boot.SecurityAutoConfiguration.class})
@ServletComponentScan
@MapperScan("com.gz.edu.dao")
public class EduApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EduApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
