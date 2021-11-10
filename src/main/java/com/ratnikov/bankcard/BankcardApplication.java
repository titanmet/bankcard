package com.ratnikov.bankcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BankcardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankcardApplication.class, args);
    }

}
