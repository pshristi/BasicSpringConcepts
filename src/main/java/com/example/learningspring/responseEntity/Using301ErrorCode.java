package com.example.learningspring.responseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Using301ErrorCode {

    @GetMapping("/old-get-user")
    public ResponseEntity<String> getUserOld() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/new-get-user");
        return ResponseEntity.status(301).headers(headers).body("Redirecting to new endpoint /new-get-user");
    }

    @GetMapping("/new-get-user")
    public ResponseEntity<String> getUserNew() {
        return ResponseEntity.status(200).body("New User Data");
    }
}
