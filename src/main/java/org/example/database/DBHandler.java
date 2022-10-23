package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends Configuration {
    Connection connection;
    public Connection getConnection() {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        try {
            connection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return connection;
    }
}
