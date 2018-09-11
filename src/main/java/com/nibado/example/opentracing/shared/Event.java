package com.nibado.example.opentracing.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String service;
    private int count;

    public Event(String service, int count) {
        this.service = service;
        this.count = count;
    }

    public Event() {
    }
}
