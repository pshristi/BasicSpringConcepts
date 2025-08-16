# Spring Data Access with JDBC and JdbcTemplate

This package demonstrates different approaches to database access in Spring, comparing plain JDBC with Spring's JdbcTemplate abstraction.

## Key Concepts Demonstrated

### Plain JDBC (jdbc subpackage)
The `jdbc` subpackage demonstrates the traditional approach to database access using plain JDBC:

- **Manual Connection Management**: Creating and managing database connections manually
- **SQL Statement Execution**: Using Statement and PreparedStatement for executing SQL
- **Result Processing**: Processing ResultSet objects to extract data
- **Resource Management**: Using try-with-resources to ensure proper cleanup
- **Exception Handling**: Catching and handling SQLExceptions

#### Example Files:
- **DatabaseConnection.java**: Shows how to establish a database connection using the JDBC DriverManager
- **UserDao.java**: Demonstrates CRUD operations using plain JDBC

### Spring JdbcTemplate (jdbcTemplate subpackage)
The `jdbcTemplate` subpackage demonstrates Spring's JdbcTemplate abstraction, which simplifies database access:

- **Simplified API**: Reducing boilerplate code with a higher-level API
- **Automatic Resource Management**: No need to manually close connections, statements, or result sets
- **Exception Translation**: Converting JDBC's checked exceptions to Spring's unchecked DataAccessExceptions
- **Connection Pooling**: Using HikariCP for efficient connection management
- **Row Mapping**: Converting database rows to Java objects with RowMapper

#### Example Files:
- **application.properties**: Database configuration and connection pool settings
- **UserJdbcTemplate.java**: Model class for user data
- **UserRepository.java**: Demonstrates CRUD operations using JdbcTemplate

## Comparison: JDBC vs JdbcTemplate

### Plain JDBC Approach
```java
// Establish connection
Connection dbConnection = connection.getConnection();

// Execute query
try (Statement statement = dbConnection.createStatement();
     ResultSet resultSet = statement.executeQuery(sql)) {
    
    // Process results
    while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        // ...
    }
} catch (SQLException e) {
    // Handle exception
}
```

### JdbcTemplate Approach
```java
// Execute query and map results in one operation
List<UserJdbcTemplate> users = jdbcTemplate.query(
    "SELECT * FROM users", 
    (rs, rowNum) -> {
        UserJdbcTemplate user = new UserJdbcTemplate();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        // ...
        return user;
    }
);
```

## Benefits of JdbcTemplate

1. **Less Boilerplate Code**: Eliminates repetitive code for connection management and resource cleanup
2. **Automatic Exception Handling**: Translates checked SQLExceptions to unchecked DataAccessExceptions
3. **Connection Pooling**: Efficiently manages database connections with HikariCP
4. **Parameterized Queries**: Simplifies the use of parameterized queries to prevent SQL injection
5. **Batch Operations**: Provides efficient batch update operations
6. **Integration with Spring's Transaction Management**: Works seamlessly with Spring's transaction infrastructure

## Best Practices

1. **Use Parameterized Queries**: Always use parameterized queries to prevent SQL injection
2. **Configure Connection Pooling**: Set appropriate pool sizes based on your application's needs
3. **Use RowMapper for Complex Objects**: Create custom RowMappers for mapping complex objects
4. **Handle Exceptions Appropriately**: Catch and handle Spring's DataAccessExceptions
5. **Use Named Parameters**: For complex queries, use named parameters with NamedParameterJdbcTemplate

## Related Resources
- [Spring JDBC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc)
- [HikariCP Documentation](https://github.com/brettwooldridge/HikariCP)
