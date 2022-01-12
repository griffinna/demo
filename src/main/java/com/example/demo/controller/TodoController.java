package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";  // temporary user id.

            // 1. TodoEntity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // 2. id 를 null 로 초기화 (생성당시에는 id 가 없어야함)
            entity.setId(null);

            // 3. 임시 사용자 아이디 설정 - TODO :: 추후 인증, 인가 기능 추가 예정
            entity.setUserId(temporaryUserId);

            // 4. Todo엔티티 생성
            List<TodoEntity> entities = service.create(entity);

            // 5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 7. ResponseDTO 리턴
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 8. 예외 케이스인 경우 dto 대신 error에 메시지를 넣어 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";  //temporary user id.

        // 1. Todo 목록 조회
        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        // 2. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 3. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
        ResponseDTO<TodoDTO> resopnse = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 4. ResponseDTO 리턴
        return ResponseEntity.ok().body(resopnse);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";  //temporary user id.

        // 1. dto를 entity 로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);

        // 2. userId 를 temporaryUserId 로 초기화 - TODO :: 추후 인증, 인가 기능 추가 예정
        entity.setUserId(temporaryUserId);

        // 3. 서비스를 이용해 entity 업데이트
        List<TodoEntity> entities = service.update(entity);

        // 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 5. 변환된 todoDTO 리스트를 이용해 ResponseDTO 를 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 6. ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

}
