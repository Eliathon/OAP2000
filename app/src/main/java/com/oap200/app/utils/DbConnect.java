// Created by Sindre
package com.oap200.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect implements AutoCloseable {
    private String dbUrl = "jdbc:mysql://localhost:3306/classicmodels";
    private String user = "root";
    private String pass = "";
    // TODO: Change username and pw to student
    private Connection myConnection;

    public DbConnect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        myConnection = DriverManager.getConnection(dbUrl, user, pass);
    }

    public Connection getConnection() {
        return myConnection;
    }

    @Override
    public void close() throws SQLException {
        if (myConnection != null && !myConnection.isClosed()) {
            myConnection.close();
        }
    }
}
