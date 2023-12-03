/**
 * Controller class for managing employee-related operations.
 * @author Kristian
 */
package com.oap200.app.controllers;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.views.EmployeeManagementPanel;

import java.sql.SQLException;
import java.util.List;

public class EmployeeController {

    // Method for handling the display of employees
    private EmployeeDAO employeeDAO;
    private EmployeeManagementPanel employeeManagementPanel;

    /**
     * Constructor for EmployeeController.
     *
     * @param employeeDAO            The data access object for employee-related operations.
     * @param employeeManagementPanel The panel for managing the display of employees.
     */
    public EmployeeController(EmployeeDAO employeeDAO, EmployeeManagementPanel employeeManagementPanel) {
        this.employeeDAO = employeeDAO;
        this.employeeManagementPanel = employeeManagementPanel;
    }

    /**
     * Get details of an employee based on the employee number.
     *
     * @param employeeNumber The employee number to retrieve details for.
     * @return An array of employee details, or null if not found or an exception occurs.
     */
    public String[] getEmployeeDetails(String employeeNumber) {
        try {
            return employeeDAO.getEmployeeDetails(employeeNumber);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handle searching for employees by name.
     *
     * @param employeeName The name to search for.
     * @return List of search results, or an empty list if none found.
     */
    public List<String[]> handleSearchEmployees(String employeeName) {
        List<String[]> searchResults = employeeDAO.searchEmployees(employeeName);
        if (!searchResults.isEmpty()) {
            employeeManagementPanel.displayEmployees(searchResults);
        }
        return searchResults;
    }

    /**
     * Handle searching for employees by number.
     *
     * @param employeeNumber The employee number to search for.
     * @return List of search results, or an empty list if none found.
     */
    public List<String[]> handleSearchEmployeesNumber(String employeeNumber) {
        List<String[]> searchResults = employeeDAO.searchEmployeesNumber(employeeNumber);
        if (!searchResults.isEmpty()) {
            employeeManagementPanel.displayEmployees(searchResults);
        }
        return searchResults;
    }

    /**
     * Handle displaying all employees.
     */
    public void handleViewAllEmployees() {
        List<String[]> allEmployees = employeeDAO.fetchEmployees();
        employeeManagementPanel.displayEmployees(allEmployees);
        
    }
    
    /**
     * Handle deleting an employee.
     *
     * @param employeeNumber The employee number to delete.
     * @return True if deletion is successful, false otherwise.
     */
    public boolean handleDeleteEmployee(String employeeNumber) {
        boolean deletionSuccessful = employeeDAO.deleteEmployee(employeeNumber);
    
        // If deletion is successful, refresh the employee list
        if (deletionSuccessful) {
            handleViewAllEmployees(); // Refresh the list of employees
            return true;
        } else {
            // Handle errors here, for example, display an error message
            return false;
        }
    }
    

    /**
     * Handle adding a new employee.
     *
     * @param lastName    The last name of the new employee.
     * @param firstName   The first name of the new employee.
     * @param extension   The extension of the new employee.
     * @param email       The email of the new employee.
     * @param officeCode  The office code of the new employee.
     * @param reportsTo   The employee to whom the new employee reports.
     * @param jobTitle    The job title of the new employee.
     * @return True if addition is successful, false otherwise.
     */
    public boolean handleAddEmployee(String lastName, String firstName, String extension, String email,
                                 String officeCode, Integer reportsTo, String jobTitle) {
    try {
        // Generate the next employee number
        String employeeNumber = employeeDAO.getNextAvailableEmployeeNumber();

        // Call addEmployee method with the new employee number
        boolean isAdded = employeeDAO.addEmployee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
        
        // Refresh the employee list if addition is successful
        if (isAdded) {
            handleViewAllEmployees();
        }
        return isAdded;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}


    /**
     * Retrieve the EmployeeDAO instance.
     *
     * @return The EmployeeDAO instance.
     */
    public EmployeeDAO getEmployeeDAO() {
        return this.employeeDAO;
    }

    /**
     * Retrieve the list of employee roles.
     *
     * @return The list of employee roles.
     */
    public List<String> getEmployeeRoles() {
        return EmployeeDAO.getEmployeeRoles();
    }

    /**
     * Handle updating an employee.
     *
     * @param employeeNumber The employee number to update.
     * @param lastName       The last name of the employee.
     * @param firstName      The first name of the employee.
     * @param extension      The extension of the employee.
     * @param email          The email of the employee.
     * @param officeCode     The office code of the employee.
     * @param reportsTo      The employee to whom the employee reports.
     * @param jobTitle       The job title of the employee.
     * @return True if update is successful, false otherwise.
     */
    public boolean handleUpdateEmployee(String employeeNumber, String lastName, String firstName, String extension,
                                    String email, String officeCode, Integer reportsTo, String jobTitle) {
    // Basic validation (you can expand this as needed)
    if (employeeNumber == null || employeeNumber.trim().isEmpty()) {
        // Handle invalid input
        System.out.println("Invalid input for Employee Number. The input field is either empty, or this employee doesn't exist");
        return false;
    }

    // Call update method in DAO and check if the update is successful
    boolean isUpdated = employeeDAO.updateEmployee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);

    // Refresh the employee list if update is successful
    if (isUpdated) {
        handleViewAllEmployees();
    }

    return isUpdated;
}

}
