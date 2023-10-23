package org.example;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.database.h2.H2Connection;
import org.flywaydb.core.internal.jdbc.DriverDataSource;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final Main INSTANCE = new Main();

    private static Connection connector;

    public static Connection getConnector() {
        return connector;
    }

    private static PreparedStatement createSt;
    private static PreparedStatement readSt;

    public static void main(String[] args) throws SQLException {

            try {
                String dbUrl = "jdbc:h2:~/test";
                String username = "sa";
                String password = "";
                connector = DriverManager.getConnection(dbUrl, username, password);
                flywayMigration(dbUrl, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }

           List<Client> client = INSTANCE.listAll();
        System.out.println(client);

    }

    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, name FROM client";

        try (Connection connection = Database.getConnector();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Client client = new Client(id, name);
                clients.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating client: " + e.getMessage(), e);
        }

        return clients;
    }


    private static void flywayMigration(String url, String username, String password) {
        Flyway flyway = Flyway.configure().dataSource(url, username, password).load();
        flyway.migrate();
    }
}