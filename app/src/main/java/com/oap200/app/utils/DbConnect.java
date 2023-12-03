// Created by Sindre
package com.oap200.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect implements AutoCloseable {
    private String dbUrl = "jdbc:mysql://localhost:3306/classicmodels";
    private String user = "root";
    private String pass = "Mirjam4321";
    // TODO: Change username and pw to student
    private Connection myConnection;

    public DbConnect() throws SQLException, ClassNotFoundException {
        // Load JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish connection
        myConnection = DriverManager.getConnection(dbUrl, user, pass);
    }

/**
 * Retrieves the established database connection.
 *
 * @return The active database connection.
 */    public Connection getConnection() {
        return myConnection;
    }

    /**
 * Closes the database connection if it's open.
 *
 * @throws SQLException If an error occurs while closing the connection.
 */
    @Override
public void close() throws SQLException {
    if (myConnection != null && !myConnection.isClosed()) {
        myConnection.close();
    }
}
}
