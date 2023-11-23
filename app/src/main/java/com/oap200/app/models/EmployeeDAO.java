package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<String[]> fetchEmployees() {
        List<String[]> employees = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM employees");

            while (myRs.next()) {
                String[] employee = new String[] {
                        myRs.getString("employeeNumber"),
                        myRs.getString("lastName"),
                        myRs.getString("firstName"),
                        myRs.getString("extension"),
                        myRs.getString("email"),
                        myRs.getString("officeCode"),
                        myRs.getString("reportsTo"),
                        myRs.getString("jobTitle"),
                };
                employees.add(employee);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return employees;
    }
  
    public List<String[]> searchNum(String empNumber) {
        List<String[]> employees = new ArrayList<>();
        String sql = "SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle FROM employees WHERE employeeNumber LIKE ?";

        try (Connection conn = new DbConnect().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, "%" + empNumber + "%");
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String[] employee = {
                    rs.getString("employeeNumber"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("extension"),
                    rs.getString("email"),
                    rs.getString("officeCode"),
                    rs.getString("reportsTo"),
                    rs.getString("jobTitle"),
                };
                employees.add(employee);
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return employees;
}

public List<String[]> searchName(String empName) {
        List<String[]> employees = new ArrayList<>();
        String sql = "SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle FROM employees WHERE lastName LIKE ?";

        try (Connection conn = new DbConnect().getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, "%" + empName + "%");
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String[] employee = {
                    rs.getString("employeeNumber"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("extension"),
                    rs.getString("email"),
                    rs.getString("officeCode"),
                    rs.getString("reportsTo"),
                    rs.getString("jobTitle"),
                };
                employees.add(employee);
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return employees;
}

}
