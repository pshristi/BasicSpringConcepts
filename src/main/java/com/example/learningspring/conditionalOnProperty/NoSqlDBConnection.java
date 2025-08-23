package com.example.learningspring.conditional_on_property;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "nosql", value = "enabled", havingValue = "true", matchIfMissing = false)
public class NoSqlDBConnection {
}
