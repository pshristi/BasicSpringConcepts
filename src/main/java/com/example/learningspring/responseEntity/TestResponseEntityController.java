package com.example.learningspring.responseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResponseEntityController {

    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Key1", "Value1");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body("User Data");
    }

    @PostMapping("/user")
    public ResponseEntity<Void> setUser(String user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Key2", "Value2");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .build();
    }

    //Here, when we are returning POJO or object directly, we will need to put @ResponseBody with @Controller else use @RestController annotation
    //so that response will not be considered as name of the view
    @GetMapping("/v1/user")
    @ResponseBody
    public String getUserV1() {
        return "User Data";
    }
}
