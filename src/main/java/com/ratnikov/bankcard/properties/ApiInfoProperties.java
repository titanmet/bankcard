package com.ratnikov.bankcard.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api-info-swagger")
@Data
public class ApiInfoProperties {
    private String name;
    private String description;
    private String version;
    private String ServiceUrl;
    private String license;
    private String licenseUrl;
}
