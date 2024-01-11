package repository;

import java.sql.*;
import java.util.Optional;

public class H2TaskRepository implements H2Repository{

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public void save(String insertDataSQL) throws SQLException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.execute();
        }
    }

    @Override
    public Optional getById(long id) throws SQLException {
        return Optional.empty();
    }
}
