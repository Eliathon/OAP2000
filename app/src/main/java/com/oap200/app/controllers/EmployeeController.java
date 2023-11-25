//Created by Kristian
package com.oap200.app.controllers;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.views.EmployeeManagementPanel;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;
public class EmployeeController {

   // Method for handling the display of products
private EmployeeDAO employeeDAO;
private EmployeeManagementPanel employeeManagementPanel;

public EmployeeController(EmployeeDAO employeeDAO, EmployeeManagementPanel employeeManagementPanel) {
    this.employeeDAO = employeeDAO;
    this.employeeManagementPanel = employeeManagementPanel;
}

// Method to handle searching for products by name
public void handleSearchEmployees(String employeeName) {
    List<String[]> searchResults = employeeDAO.searchEmployees(employeeName);
    employeeManagementPanel.displayEmployees(searchResults);
}

// Method to handle displaying all products
public void handleViewAllEmployees() {
    List<String[]> allEmployees = employeeDAO.fetchEmployees();
    employeeManagementPanel.displayEmployees(allEmployees);
}

// Method to handle deleting a product
public boolean handleDeleteEmployee(String employeeNumber) {
    boolean deletionSuccessful = employeeDAO.deleteEmployee(employeeNumber);
    if (deletionSuccessful) {
        return true;
    } else {
        // Handle errors here, for example, display an error message
        return false;
    }
}

// Method to handle adding a new employee
public boolean handleAddEmployee(String employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, Integer reportsTo, String jobTitle) {
    // Since all inputs should be strings, there is no need to parse them
    return employeeDAO.addEmployee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
}

// Method to retrieve the EmployeeDAO instance
public EmployeeDAO getEmployeeDAO() {
    return this.employeeDAO;
}

// Method to retrieve the list of product lines
public List<String> getEmployeeRoles() {
    return EmployeeDAO.getEmployeeRoles();
}

// Method to handle updating an employee
public boolean handleUpdateEmployee(String employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, Integer reportsTo, String jobTitle) {
    // Basic validation (you can expand this as needed)
    if (employeeNumber == null || employeeNumber.trim().isEmpty() ||
        lastName == null || lastName.trim().isEmpty() ||
        firstName == null || firstName.trim().isEmpty()) {
        // Handle invalid input
        System.out.println("Invalid input for employee update.");
        return false;
    }

    // Call update method in DAO and return its result
    return employeeDAO.updateEmployee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
}
}