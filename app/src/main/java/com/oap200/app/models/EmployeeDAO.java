//Created by Kristian
package com.oap200.app.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import com.oap200.app.views.EmployeeManagementPanel;

import com.oap200.app.utils.DbConnect;

public class EmployeeDAO {
    // Method to fetch all employees from the database
    public List<String[]> fetchEmployees() {
        System.out.println("View button clicked!");
        List<String[]> employees = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            
            // Execute SELECT query to retrieve all employees
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM employees");

            // Process the retrieved data and populate the 'employees' list
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
    
    // Method to search for employees by name in the database
    public List<String[]> searchEmployees(String lastName) {
        List<String[]> searchResults = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            
            // Execute prepared statement to search for employees after the given name
            PreparedStatement preparedStatement = myConnection.prepareStatement("SELECT * FROM employees WHERE lastName LIKE ?");
            preparedStatement.setString(1, "%" + lastName + "%");
            ResultSet myRs = preparedStatement.executeQuery();

            // Process the retrieved data and populate the 'searchResults' list
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
                searchResults.add(employee);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }
    
// Method to search for employees by number in the database
    public List<String[]> searchEmployeesNumber(String employeeNumber) {
        List<String[]> searchResults = new ArrayList<>();
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            
            // Execute prepared statement to search for employees after the given number
            PreparedStatement preparedStatement = myConnection.prepareStatement("SELECT * FROM employees WHERE employeeNumber = ?");
            preparedStatement.setString(1, employeeNumber);
            ResultSet myRs = preparedStatement.executeQuery();

            // Process the retrieved data and populate the 'searchResults' list
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
                System.out.println("TEST");
                searchResults.add(employee);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println("HALLO SER DU DETTE");
        return searchResults;
    }

    // Method to delete an employee from the database
    public boolean deleteEmployee(String employeeNumber) {
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            
            //Update customers table to remove references to chosen employee
            PreparedStatement updateCustomers = myConnection.prepareStatement(
                "UPDATE customers SET salesRepEmployeeNumber = NULL WHERE salesRepEmployeeNumber = ?");
                updateCustomers.setString(1, employeeNumber);
                updateCustomers.executeUpdate();

            // Execute prepared statement to delete an employee with a given code
            PreparedStatement preparedStatement = myConnection.prepareStatement("DELETE FROM employees WHERE employeeNumber = ?");
            preparedStatement.setString(1, employeeNumber);
            
            // Get the number of rows affected after the deletion
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Return true if deletion was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // Method to retrieve distinct employeeroles from the database
    public static List<String> getEmployeeRoles() {
        List<String> employeeRoles = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            
            // Execute SELECT query to retrieve the employee roles 
            ResultSet myRs = myStmt.executeQuery("SELECT DISTINCT jobTitle FROM employees");

            // Process the retrieved data and populate the 'employeeRoles' list
            while (myRs.next()) {
                String role = myRs.getString("jobTitle");
                employeeRoles.add(role);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return employeeRoles;
    }
    
    // Method to add a new employee to the database
    public boolean addEmployee(String employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, Integer reportsTo, String jobTitle) {
        Connection myConnection = null;

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            myConnection = db.getConnection();

            // Prepare the INSERT query
            PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, employeeNumber);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, extension);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, officeCode);
            preparedStatement.setInt(7, reportsTo);
            preparedStatement.setString(8, jobTitle);

            // Validate input data
            if (employeeNumber == null || employeeNumber.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
                System.out.println("Employee number and last name is required.");
                return false;
            }

            // Start transaction
            myConnection.setAutoCommit(false);

            // Execute the INSERT query
            int rowsAffected = preparedStatement.executeUpdate();

            // Confirm the transaction if the addition was successful
            if (rowsAffected > 0) {
                myConnection.commit();
                System.out.println("Employee added successfully");
            } else {
                // Roll back the transaction if the addition failed
                myConnection.rollback();
                System.out.println("Failed to add employee");
            }

            // Log the number of affected rows (for troubleshooting)
            System.out.println("Rows affected: " + rowsAffected);

            // Return true if the addition was successful
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            // Log any exceptions
            ex.printStackTrace();
            return false;

        } finally {
            try {
                if (myConnection != null) {
                    // Set back to autocommit=true
                    myConnection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Method to update an employee in the database
    public boolean updateEmployee(String employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, Integer reportsTo, String jobTitle) {
        try {
            
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            PreparedStatement preparedStatement = myConnection.prepareStatement(
                    "UPDATE employees SET lastName=?, firstName=?, extension=?, email=?, officeCode=?, reportsTo=?, jobTitle=? WHERE employeeNumber=?");

            // Set parameters for the UPDATE query
           
        preparedStatement.setString(1, lastName);
        preparedStatement.setString(2, firstName);
        preparedStatement.setString(3, extension);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, officeCode);
           preparedStatement.setInt(6, reportsTo);
        preparedStatement.setString(7, jobTitle);
        preparedStatement.setString(8, employeeNumber);

            // Execute the UPDATE query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if the update was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
