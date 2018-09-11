package com.nibado.example.opentracing.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RecursiveService {
    private final EventRepository repository;
    private final RestTemplate restTemplate;
    private final String appName;

    public RecursiveService(EventRepository repository, RestTemplate restTemplate, @Value("${spring.application.name}") String appName) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.appName = appName;
    }

    public int recursive(int count) {
        if(count > 1) {
            call(count - 1);
        }

        repository.save(new Event(appName, count));

        log.info("{}: {}, events: {}", appName, count, repository.count());

        return count;
    }

    private String url() {
        if(appName.equals("foo-service")) {
            return "http://localhost:8081/recursive/%s";
        } else {
            return "http://localhost:8080/recursive/%s";
        }
    }

    private Response call(int count) {
        ResponseEntity<Response> responseEntity = restTemplate.getForEntity(String.format(url(), count), Response.class);

        return responseEntity.getBody();
    }
}
