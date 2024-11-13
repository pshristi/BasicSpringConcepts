package com.example.learningspring.springJpa.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    public void createUserTable() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection dbConnection = connection.getConnection();

        // Create the table
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL)";

        try (Statement statement = dbConnection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
        }
    }

    public void insertUser(String name, String email) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection dbConnection = connection.getConnection();

        String sql = "INSERT INTO users (name, email) VALUES (?,?)";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    public void getUsers() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection dbConnection = connection.getConnection();

        String sql = "SELECT * FROM users";

        try (Statement statement = dbConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.printf("%d, %s, %s%n", id, name, email);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }
}
