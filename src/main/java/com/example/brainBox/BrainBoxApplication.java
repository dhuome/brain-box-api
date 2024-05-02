package com.example.brainBox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BrainBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrainBoxApplication.class, args);
    }

}
