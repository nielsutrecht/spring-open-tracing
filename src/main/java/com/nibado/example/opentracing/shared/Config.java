package com.nibado.example.opentracing.shared;

import brave.Tracing;
import brave.okhttp3.TracingCallFactory;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

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

    public Client client() {
        String url;

        if(appName.equals("foo-service")) {
            url = "http://localhost:8081/recursive/";
        } else {
            url = "http://localhost:8080/recursive/";
        }

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .callFactory(TracingCallFactory.create(Tracing.current(), client))
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        return retrofit.create(Client.class);
    }
}
