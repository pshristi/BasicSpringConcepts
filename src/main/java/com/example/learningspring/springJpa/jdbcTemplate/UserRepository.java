package com.example.learningspring.springJpa.jdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//@Respository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL)";

        jdbcTemplate.execute(sql);
    }

    public void insertUser(UserJdbcTemplate user) {
        String sql = "INSERT INTO users (name, email) VALUES (?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail());
    }

    public List<UserJdbcTemplate> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                    UserJdbcTemplate user = new UserJdbcTemplate();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
        );
    }
}
