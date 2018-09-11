package com.nibado.example.opentracing.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RecursiveController {
    private final RecursiveService service;

    public RecursiveController(RecursiveService service) {
        this.service = service;
    }

    @GetMapping("/recursive/{count}")
    public Response recursive(@PathVariable int count) {
        return new Response(Integer.toString(service.recursive(count)));
    }
}
