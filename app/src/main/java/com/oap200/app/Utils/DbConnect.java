/*
 * Created by Sindre
 */

package com.oap200.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    private String dbUrl = "jdbc:mysql://localhost:3306/classicmodels";
    private String user = "root";
    private String pass = "";
    private Connection myConnection;

    public DbConnect() throws SQLException, ClassNotFoundException {
        // Load JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish connection
        myConnection = DriverManager.getConnection(dbUrl, user, pass);
    }

    // Method to retrieve the connection if needed
    public Connection getConnection() {
        return myConnection;
    }
}
