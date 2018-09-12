package com.nibado.example.opentracing.shared;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    @HystrixCommand(fallbackMethod = "fallback")
    public int recursive(int count) {
        if(count > 1) {
            call(count - 1);
        }

        repository.save(new Event(config.appName, count));

        log.info("{}: {}, events: {}", config.appName, count, repository.count());

        return count;
    }

    public int fallback() {
        return 42;
    }

    private Response call(int count) {
        if(config.fail()) {
            throw new RuntimeException();
        }
        //return restTemplate.getForEntity(config.url(count), Response.class).getBody();
        randomSleep();
        try {
            return config.client().count(count).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void randomSleep() {
        long duration = (long)(Math.random() * 500.0);

        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
