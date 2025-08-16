package com.example.learningspring.springJpa.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return java.sql.DriverManager.getConnection("jdbc:postgres://localhost:3306/my_database", "username", "password");
        }
        catch (SQLException | ClassNotFoundException e) {
            //handle exception here
        }
        return null;
    }
}
