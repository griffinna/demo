package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j      // 로그 라이브러리 사용
@Service    // 비즈니스 로직을 수행하는 서비스 레이어
public class TodoService {

    public static final String ENTITY_CANNOT_BE_NULL = "Entity cannot be null";
    public static final String UNKNOWN_USER = "Unknown User.";
    @Autowired
    private TodoRepository repository;

    public String testService() {

        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        // Validations
        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn(ENTITY_CANNOT_BE_NULL);
            throw new RuntimeException(ENTITY_CANNOT_BE_NULL);
        }

        if (entity.getUserId() == null) {
            log.warn(UNKNOWN_USER);
            throw new RuntimeException(UNKNOWN_USER);
        }
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }
}
