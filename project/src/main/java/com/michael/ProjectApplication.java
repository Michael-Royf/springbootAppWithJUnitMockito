package com.michael;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectApplication {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }



    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
