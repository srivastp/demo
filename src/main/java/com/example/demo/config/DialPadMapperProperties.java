package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix="dialpad-mapper")
public class DialPadMapperProperties {
    private HashMap<String, List<String>> data = new HashMap<>();

}
