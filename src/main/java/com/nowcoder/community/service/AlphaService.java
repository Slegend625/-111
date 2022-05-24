package com.nowcoder.community.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class AlphaService {

    public AlphaService(){
    }

    @PostConstruct
    public void a(){
        System.out.println("PostConstruct");
    }

    @PreDestroy
    public void b(){
        System.out.println("PreDestroy");
    }
}
