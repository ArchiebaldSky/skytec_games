package repository;

import model.Task;

import java.sql.*;
import java.util.Optional;

public interface H2Repository<T> {

    void save(T object) throws SQLException;



    Optional getById(long id) throws SQLException;
}
