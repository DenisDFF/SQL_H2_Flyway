package org.example;

import org.flywaydb.core.Flyway;

import java.sql.*;

public class Database {

    private static final Database INSTANCE = new Database();

    private static Connection connector;

    public static Connection getConnector() {
        return connector;
    }

    private Database() {
        H2Data();
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    private void H2Data() {

        try {
            String dbUrl = "jdbc:h2:~/test";
            String username = "sa";
            String password = "";
            connector = DriverManager.getConnection(dbUrl, username, password);
            flywayMigration(dbUrl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate (String query) {
        try {
            PreparedStatement statement = connector.prepareStatement(query);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeResult(String query) {
        try {
            PreparedStatement statement = connector.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeClose () {
        try {
            connector.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void flywayMigration(String url, String username, String password) {
        Flyway flyway = Flyway.configure().dataSource(url, username, password).load();
        flyway.migrate();
    }
}
