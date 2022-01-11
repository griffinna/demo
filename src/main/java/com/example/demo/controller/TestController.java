package com.example.demo.controller;

import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.web.bind.annotation.*;

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


}
