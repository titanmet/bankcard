package com.ratnikov.bankcard.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message")
@Data
public class MessageProperties {
    private String userError;
    private String userSuccess;
    private String welcome;
    private String banner;
    private String cardError;
    private String categoryError;
    private String customerError;
}