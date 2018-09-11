package com.nibado.example.opentracing.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.nibado.example.opentracing.shared")
@EntityScan("com.nibado.example.opentracing.shared")
public class Config {
    public final String appName;

    public Config(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    public String url(int count) {
        if(appName.equals("foo-service")) {
            return String.format("http://localhost:8081/recursive/%s", count);
        } else {
            return String.format("http://localhost:8080/recursive/%s", count);
        }
    }
}
