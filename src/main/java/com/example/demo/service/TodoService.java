package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<TodoEntity> update(final TodoEntity entity) {
        // 1. 저장할 엔티티 유효성 검증
        validate(entity);

        // 2. 엔티티 id 로 TodoEntity 조회 (존재하지 않으면 업데이트 불가)
        Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            // 3. 반환된 TodoEntity 가 존재하면 값을 새 entity 값으로 덮어씌움
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // 4. 데이터베이스에 새 값을 저장
            repository.save(todo);
        });

        // 5. 사용자의 모든 Todo 리스트를 리턴
        return retrieve(entity.getUserId());
    }

}
