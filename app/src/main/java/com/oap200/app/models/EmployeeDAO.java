/**
 * Data Access Object (DAO) class for handling employee data.
 * Manages interactions with the database related to employees.
 *
 * <p>This class provides methods to retrieve, add, update, and delete employee records
 * in the database. It also includes functionality to fetch all employees, search for
 * employees by name or number, and retrieve distinct employee roles.
 *
 * <p>Database connectivity is managed through the {@link com.oap200.app.utils.DbConnect} class,
 * and the methods in this class throw {@link java.sql.SQLException} and {@link ClassNotFoundException}
 * to handle potential database access errors.
 *
 * <p>Usage example:
 * <pre>{@code
 * EmployeeDAO employeeDAO = new EmployeeDAO();
 * String[] employeeDetails = employeeDAO.getEmployeeDetails("123");
 * }</pre>
 *
 * @author Kristian
 * @version 1.0
 * @since 2023-11-30
 */
package com.oap200.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.oap200.app.utils.DbConnect;

/**
 * Data Access Object (DAO) class for handling employee data.
 * Manages interactions with the database related to employees.
 */
public class EmployeeDAO {

    /**
     * Retrieves the details of an employee based on the employee number.
     *
     * @param employeeNumber The employee number to retrieve details for.
     * @return An array containing the employee details, or null if no employee found with the given number.
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public String[] getEmployeeDetails(String employeeNumber) throws SQLException, ClassNotFoundException {
        try (Connection connection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle FROM employees WHERE employeeNumber = ?")) {

            preparedStatement.setString(1, employeeNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new String[]{
                            resultSet.getString("employeeNumber"),
                            resultSet.getString("lastName"),
                            resultSet.getString("firstName"),
                            resultSet.getString("extension"),
                            resultSet.getString("email"),
                            resultSet.getString("officeCode"),
                            resultSet.getString("reportsTo"),
                            resultSet.getString("jobTitle"),
                    };
                }
            }
        }
        return null;
    }

    /**
     * Checks if an employee exists in the database based on the employee number.
     *
     * @param employeeNumber The employee number to check for existence.
     * @return True if the employee exists, false otherwise.
     */
    public boolean doesEmployeeExist(String employeeNumber) {
        try (Connection connection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM employees WHERE employeeNumber = ?")) {
            preparedStatement.setString(1, employeeNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String generatedEmployeeNumber;

    /**
     * Retrieves the next available employee number by incrementing the maximum existing employee number.
     *
     * @return The next available employee number.
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public String getNextAvailableEmployeeNumber() throws SQLException, ClassNotFoundException {
        // Initialize the DbConnect object
        DbConnect db = new DbConnect();
        // Use the object to get a connection
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(employeeNumber) as maxNumber FROM employees");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String maxNumber = resultSet.getString("maxNumber");
                if (maxNumber != null && !maxNumber.trim().isEmpty()) {
                    // Assumes employeeNumber is a numeric string that can be parsed into an integer
                    int nextNumber = Integer.parseInt(maxNumber.trim()) + 1;
                    generatedEmployeeNumber = String.format("%04d", nextNumber); // Pad with zeros if necessary
                } else {
                    // Default employee number to start if no employees exist
                    generatedEmployeeNumber = "0001";
                }
            } else {
                throw new SQLException("Unable to retrieve the highest employee number.");
            }
        }
        return generatedEmployeeNumber;
    }

    /**
     * Gets the most recently generated employee number.
     *
     * @return The most recently generated employee number.
     */
    public String getGeneratedEmployeeNumber() {
        return generatedEmployeeNumber;
    }

/**
 * Method to fetch all employees from the database.
 *
 * @return A list of arrays, each containing details of an employee.
 */
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
            String[] employee = new String[]{
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

/**
 * Method to search for employees by name in the database.
 *
 * @param lastName The last name to search for.
 * @return A list of arrays, each containing details of an employee matching the search criteria.
 */
public List<String[]> searchEmployees(String lastName) {
    List<String[]> searchResults = new ArrayList<>();

    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // Execute prepared statement to search for employees after the given name
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("SELECT * FROM employees WHERE lastName LIKE ?");
        preparedStatement.setString(1, "%" + lastName + "%");
        ResultSet myRs = preparedStatement.executeQuery();

        // Process the retrieved data and populate the 'searchResults' list
        while (myRs.next()) {
            String[] employee = new String[]{
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
/**
 * Method to search for employees by number in the database.
 *
 * @param employeeNumber The employee number to search for.
 * @return A list of arrays, each containing details of an employee matching the search criteria.
 */
public List<String[]> searchEmployeesNumber(String employeeNumber) {
    List<String[]> searchResults = new ArrayList<>();
    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // Execute prepared statement to search for employees after the given number
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("SELECT * FROM employees WHERE employeeNumber = ?");
        preparedStatement.setString(1, employeeNumber);
        ResultSet myRs = preparedStatement.executeQuery();

        // Process the retrieved data and populate the 'searchResults' list
        while (myRs.next()) {
            String[] employee = new String[]{
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

/**
 * Method to delete an employee from the database.
 *
 * @param employeeNumber The employee number to be deleted.
 * @return True if the deletion was successful, false otherwise.
 */
public boolean deleteEmployee(String employeeNumber) {
    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // Update customers table to remove references to the chosen employee
        PreparedStatement updateCustomers = myConnection.prepareStatement(
                "UPDATE customers SET salesRepEmployeeNumber = NULL WHERE salesRepEmployeeNumber = ?");
        updateCustomers.setString(1, employeeNumber);
        updateCustomers.executeUpdate();

        // Execute prepared statement to delete an employee with a given code
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("DELETE FROM employees WHERE employeeNumber = ?");

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

/**
 * Method to retrieve distinct employee roles from the database.
 *
 * @return A list of distinct employee roles.
 */
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
/**
 * Method to add a new employee to the database.
 *
 * @param employeeNumber The employee number.
 * @param lastName       The last name of the employee.
 * @param firstName      The first name of the employee.
 * @param extension      The extension of the employee.
 * @param email          The email address of the employee.
 * @param officeCode     The office code of the employee.
 * @param reportsTo      The employee to whom the new employee reports.
 * @param jobTitle       The job title of the employee.
 * @return True if the addition was successful, false otherwise.
 */
public boolean addEmployee(String employeeNumber, String lastName, String firstName, String extension, String email,
                           String officeCode, Integer reportsTo, String jobTitle) {
    Connection myConnection = null;

    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        myConnection = db.getConnection();

        // Prepare the INSERT query
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, employeeNumber);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, firstName);
        preparedStatement.setString(4, extension);
        preparedStatement.setString(5, email);
        preparedStatement.setString(6, officeCode);
        if (reportsTo != null) {
            preparedStatement.setInt(7, reportsTo);
        } else {
            preparedStatement.setNull(7, java.sql.Types.INTEGER);
        }
        preparedStatement.setString(8, jobTitle);

        // Validate input data
        if (employeeNumber == null || employeeNumber.trim().isEmpty() || lastName == null
                || lastName.trim().isEmpty()) {
            System.out.println("Employee number and last name are required.");
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

    } catch (SQLException | ClassNotFoundException ex) {
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

/**
 * Method to update an employee in the database.
 *
 * @param employeeNumber The employee number.
 * @param lastName       The last name of the employee.
 * @param firstName      The first name of the employee.
 * @param extension      The extension of the employee.
 * @param email          The email address of the employee.
 * @param officeCode     The office code of the employee.
 * @param reportsTo      The employee to whom the new employee reports.
 * @param jobTitle       The job title of the employee.
 * @return True if the update was successful, false otherwise.
 */
public boolean updateEmployee(String employeeNumber, String lastName, String firstName, String extension,
                              String email, String officeCode, Integer reportsTo, String jobTitle) {
    try {
        Connection myConnection = new DbConnect().getConnection();
        String sql = "UPDATE employees SET lastName = COALESCE(?, lastName), " +
                     "firstName = COALESCE(?, firstName), " +
                     "extension = COALESCE(?, extension), " +
                     "email = COALESCE(?, email), " +
                     "officeCode = COALESCE(?, officeCode), " +
                     "reportsTo = COALESCE(?, reportsTo), " +
                     "jobTitle = COALESCE(?, jobTitle) WHERE employeeNumber = ?";
        PreparedStatement preparedStatement = myConnection.prepareStatement(sql);

        preparedStatement.setString(1, isEmpty(lastName) ? null : lastName);
        preparedStatement.setString(2, isEmpty(firstName) ? null : firstName);
        preparedStatement.setString(3, isEmpty(extension) ? null : extension);
        preparedStatement.setString(4, isEmpty(email) ? null : email);
        preparedStatement.setString(5, isEmpty(officeCode) ? null : officeCode);
        if (reportsTo == null || reportsTo == 0) {
            preparedStatement.setNull(6, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(6, reportsTo);
        }
        preparedStatement.setString(7, isEmpty(jobTitle) ? null : jobTitle);
        preparedStatement.setString(8, employeeNumber);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
        return false;
    }
}

/**
 * Checks if a string is empty (null or whitespace).
 *
 * @param str The string to check.
 * @return True if the string is empty, false otherwise.
 */
private boolean isEmpty(String str) {
    return str == null || str.trim().isEmpty();
}
}
