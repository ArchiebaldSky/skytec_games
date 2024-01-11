package repository;

import java.sql.*;
import java.util.Optional;

public interface H2Repository {

    void save(String insertDataSQL) throws SQLException;

    Optional getById(long id) throws SQLException;
}
