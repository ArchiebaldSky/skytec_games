package repository;

import model.Clan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class H2ClanRepository implements H2Repository {

    public H2ClanRepository() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS clans (id BIGINT PRIMARY KEY AUTOINCREMENT, name VARCHAR(20), gold INT);";
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        }
    }

    @Override
    public Optional<Clan> getById(long id) throws SQLException {
        String selectDataSQL = String.format("SELECT * FROM clans WHERE id = %d;", id);
        Connection connection = getConnection();
        Optional<Clan> optionalClan = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int gold = Integer.parseInt(resultSet.getString("gold"));
                Clan clan = Clan.builder()
                        .id(id)
                        .name(name)
                        .gold(new AtomicInteger(gold))
                        .build();
                optionalClan = Optional.of(clan);
            }
        }
        return optionalClan;
    }


    public List<Long> getClanIds() throws SQLException {
        String selectDataSQL = "SELECT id FROM clans;";
        Connection connection = getConnection();
        List<Long> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                result.add(id);
            }
        }
        return result;
    }

    public void addClan(String name) throws SQLException {
        String insertDataSQL = String.format("INSERT INTO clans (name, gold) VALUES (%s, 0);", name);
        save(insertDataSQL);
    }

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


}
