package org.example;

import org.flywaydb.core.Flyway;

import java.sql.*;
import java.util.List;


public class Main {

    private static Connection connector;

    public static Connection getConnector() {
        return connector;
    }

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

            ClientService clientService = new ClientService();

           List<Client> client = clientService.listAll();
        System.out.println(client);

    }


    private static void flywayMigration(String url, String username, String password) {
        Flyway flyway = Flyway.configure().dataSource(url, username, password).load();
        flyway.migrate();
    }
}