package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service    // 비즈니스 로직을 수행하는 서비스 레이어
public class TodoService {
    public String testService() {
        return "Test Service";
    }
}
