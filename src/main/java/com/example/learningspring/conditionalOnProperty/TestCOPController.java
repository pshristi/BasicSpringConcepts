package com.example.learningspring.conditional_on_property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TestCOPController {
    @Autowired(required = false)
    SqlDBConnection sqlDBConnection;

    @Autowired(required = false)
    NoSqlDBConnection noSqlDBConnection;

    @GetMapping("/test-conditional-on-property")
    public void testConditionalOnProperty() {
        System.out.println(Objects.nonNull(sqlDBConnection)? "sql is enabled" : "");
        System.out.println(Objects.nonNull(noSqlDBConnection)? "nosql is enabled" : "");
    }
}
