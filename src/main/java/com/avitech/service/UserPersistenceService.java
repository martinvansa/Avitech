package com.avitech.service;

import com.avitech.commandQueue.CommandQueue;
import com.avitech.exception.UserDataException;
import com.avitech.model.Command;
import com.avitech.model.User;

import java.sql.*;
import java.util.List;

public class UserPersistenceService {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final String ADD = "Add";
    private static final String PRINT_All = "PrintAll";
    private static final String DELETE_All = "DeleteAll";


    public void initDatabase(CommandQueue commandQueue) throws UserDataException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            System.out.println("The connection to the H2 database was successful!");
            createTable(connection);
            while (true) {
                Command command = commandQueue.consume();
                if (ADD.equals(command.getCommand())) {
                    insertData(connection, createUser(command.getOrder()));
                }
                if (PRINT_All.equals(command.getCommand())) {
                    if (0 < getCountRecords(connection)) {
                        fetchAllRecords(connection);
                    }
                }
                if (DELETE_All.equals(command.getCommand())) {
                    if (0 < getCountRecords(connection)) {
                        deleteAllUser(connection);
                    }
                }
            }
        } catch (SQLException e) {
            throw new UserDataException("Failed to create connection and table 'users'.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected User createUser(int i) {
        List<User> users = List.of(
                new User("a1", "Robert"),
                new User("a2", "Martin"));
        if (i < 1 || i > users.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        return users.get(i-1);
    }

    protected int getCountRecords(Connection connection) throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM users";
        try (PreparedStatement countStatement = connection.prepareStatement(countSQL)) {
            ResultSet resultSet = countStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("Number of records in the 'users' table: " + count);
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void createTable (Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "user_guid VARCHAR(255) NOT NULL, "
                + "user_name VARCHAR(255) NOT NULL)";
        try (PreparedStatement createTableStatement  = connection.prepareStatement(createTableSQL)) {
            createTableStatement.execute();
            System.out.println("The 'users' table has been created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(Connection connection, User user) throws UserDataException {
        String insertSQL = "INSERT INTO users (user_guid, user_name) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
            insertStatement.setString(1, user.getUserGuid());
            insertStatement.setString(2, user.getUserName());
            insertStatement.executeUpdate();
            System.out.println("The data was inserted into the 'users' table.");
        } catch (SQLException e) {
            throw new UserDataException("Failed to insert data into table 'users'.");
    }
    }

    public void fetchAllRecords(Connection connection) throws UserDataException {
        String querySQL = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             var rs = stmt.executeQuery(querySQL)) {
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String user_guid = rs.getString("user_guid");
                String user_name = rs.getString("user_name");
                System.out.println("user_id: " + user_id + ", user_guid: " + user_guid + ", user_name: " + user_name);
            }
        } catch (SQLException e) {
            throw new UserDataException("Failed to fetch data from the table 'users'.");
    }
    }

    public void deleteAllUser(Connection connection) throws UserDataException {
        String querySQL = "DELETE FROM users";
        try (Statement stmt = connection.createStatement()) {
             int rows = stmt.executeUpdate(querySQL);
            System.out.println("Number of rows deleted: " + rows);
        } catch (SQLException e) {
            throw new UserDataException("Failed to delete data from the table 'users'.");
        }
    }
}
