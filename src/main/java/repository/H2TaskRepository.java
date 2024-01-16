package repository;

import model.Task;
import java.sql.*;
import java.util.Optional;

public class H2TaskRepository implements H2Repository<Task> {

    private final String JDBC_URL = "jdbc:h2:./src/main/resources/testdb";
    private final String JDBC_USER = "sa";
    private final String JDBC_PASSWORD = "";

//    public H2TaskRepository() {
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
//            createTable(connection);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (id INT AUTO_INCREMENT PRIMARY KEY, "
                + "task_description VARCHAR(50), created_at TIMESTAMP, completed_at TIMESTAMP);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        }
    }



//    @Override
//    public void save(Task task) throws SQLException {
//        String insertTaskSQL = "INSERT INTO tasks (task_description, created_at, completed_at) VALUES (?, ?, ?);";
//        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(insertTaskSQL)) {
//            preparedStatement.setString(1, task.getDescription());
//            preparedStatement.setObject(2, task.getCreatedAt());
//            preparedStatement.setObject(3, task.getCompletedAt());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void save(Task task) throws SQLException {
        System.out.println(task.toString());
    }

    @Override
    public Optional getById(long id) throws SQLException {
        return Optional.empty();
    }
}
