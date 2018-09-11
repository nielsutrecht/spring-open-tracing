package com.nibado.example.opentracing.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RecursiveService {
    private final EventRepository repository;
    private final RestTemplate restTemplate;
    private final Config config;

    public RecursiveService(EventRepository repository, RestTemplate restTemplate, Config config) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public int recursive(int count) {
        if(count > 1) {
            call(count - 1);
        }

        repository.save(new Event(config.appName, count));

        log.info("{}: {}, events: {}", config.appName, count, repository.count());

        return count;
    }

    private Response call(int count) {
        return restTemplate.getForEntity(config.url(count), Response.class).getBody();

//        try {
//            return config.client().count(count).execute().body();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
