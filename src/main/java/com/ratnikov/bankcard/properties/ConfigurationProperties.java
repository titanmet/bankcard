package com.ratnikov.bankcard.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "config")
@Data
public class ConfigurationProperties {
    private int pageSize;
}
