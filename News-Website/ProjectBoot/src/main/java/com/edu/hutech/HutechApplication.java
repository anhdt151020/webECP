package com.edu.hutech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HutechApplication {
    public static void main(String[] args) {
        SpringApplication.run(HutechApplication.class, args);
    }
}
