package org.example;

import org.flywaydb.core.Flyway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public long create(String name) {
        String sql = "INSERT INTO CLIENT (name) VALUES (?)";

        try (Connection connection = Database.getConnector();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating client: " + e.getMessage(), e);
        }
    }

    public String getById(long id) {
        String sql = "SELECT name FROM client WHERE id = ?";

        try (Connection connection = Database.getConnector();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getString("name");
            } else {
                throw new IllegalArgumentException("Client not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating client: " + e.getMessage(), e);
        }
    }

    public void setName(long id, String name) {
        String sql = "UPDATE client SET name = ? WHERE id = ?";

        try (Connection connection = Database.getConnector();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setLong(2, id);

            int rowsUpdate = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creating client: " + e.getMessage(), e);
        }
    }

    public List<org.example.Client> listAll() {
        List<org.example.Client> clients = new ArrayList<>();
        String sql = "SELECT id, name FROM client";

        try (Connection connection = Database.getConnector();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                org.example.Client client = new org.example.Client(id, name);
                clients.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating client: " + e.getMessage(), e);
        }

        return clients;
    }
}


