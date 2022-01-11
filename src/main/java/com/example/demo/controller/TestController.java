package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // http 관련 코드 및 요청/응답 매핑
@RequestMapping("test") // resource
public class TestController {

    @GetMapping
    public String testController() {
        return "Hello World!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello World! testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return String.format("Hello World! ID : %d", id);
    }

    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return String.format("Hello World! ID : %d", id);
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return String.format("Hello World! ID : %d Message : %s", testRequestBodyDTO.getId(), testRequestBodyDTO.getMessage());
    }

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDTO");
        return ResponseDTO.<String>builder().data(list).build();
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. Are you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        // ResponseEntity : HTTP 응답의 Body, status, header 조작가능
        // set http status 400
        return ResponseEntity.badRequest().body(response);
    }

}
