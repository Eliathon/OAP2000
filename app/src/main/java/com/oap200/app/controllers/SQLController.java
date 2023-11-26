package com.oap200.app.controllers;

import com.oap200.app.utils.DbConnect;
import java.sql.*;

public class SQLController {
    public String executeQuery(String query) {
        StringBuilder result = new StringBuilder();
        try (DbConnect dbConnect = new DbConnect();
                Connection conn = dbConnect.getConnection();
                Statement stmt = conn.createStatement()) {

            if (query.trim().toLowerCase().startsWith("select")) {
                try (ResultSet rs = stmt.executeQuery(query)) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    while (rs.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1)
                                result.append(", ");
                            result.append(rs.getString(i));
                        }
                        result.append("\n");
                    }
                }
            } else {
                int count = stmt.executeUpdate(query);
                result.append(count).append(" rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            return "Error executing query: " + e.getMessage();
        }
        return result.toString();
    }
}
