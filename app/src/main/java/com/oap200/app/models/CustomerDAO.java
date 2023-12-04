package com.oap200.app.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.oap200.app.utils.DbConnect;

public class CustomerDAO {

    // Retrieves customer details based on customer number
    public String[] getCustomerDetails(String customerNumber) throws SQLException, ClassNotFoundException {
        String sql = "SELECT customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit FROM customers WHERE customerNumber = ?";
        try (Connection connection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new String[]{
                            resultSet.getString("customerNumber"),
                            resultSet.getString("customerName"),
                            resultSet.getString("contactLastName"),
                            resultSet.getString("contactFirstName"),
                            resultSet.getString("phone"),
                            resultSet.getString("addressLine1"),
                            resultSet.getString("addressLine2"),
                            resultSet.getString("city"),
                            resultSet.getString("state"),
                            resultSet.getString("postalCode"),
                            resultSet.getString("country"),
                            resultSet.getString("salesRepEmployeeNumber"),
                            resultSet.getString("creditLimit")
                    };
                }
            }
        }
        return null;
    }

    // Checks if a customer exists based on customer number
    public boolean doesCustomerExist(String customerNumber) {
        String sql = "SELECT COUNT(*) FROM customers WHERE customerNumber = ?";
        try (Connection connection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
   
private String generatedCustomerNumber;
    public String getNextAvailableCustomerNumber() throws SQLException, ClassNotFoundException {
        // Initialize the DbConnect object
        DbConnect db = new DbConnect();
        // Use the object to get a connection
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(customerNumber) as maxNumber FROM customers");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String maxNumber = resultSet.getString("maxNumber");
                if (maxNumber != null && !maxNumber.trim().isEmpty()) {
                    // Assumes employeeNumber is a numeric string that can be parsed into an integer
                    int nextNumber = Integer.parseInt(maxNumber.trim()) + 1;
                    generatedCustomerNumber = String.format("%04d", nextNumber); // Pad with zeros if necessary
                } else {
                    // Default employee number to start if no employees exist
                    generatedCustomerNumber = "0001";
                }
            } else {
                throw new SQLException("Unable to retrieve the highest customer number.");
            }
        }
        return generatedCustomerNumber;
    }

public String getGeneratedCustomerNumber() {
    
    return generatedCustomerNumber;
}

    // Fetch all customers from the database
    public List<String[]> fetchCustomers() {
        List<String[]> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection connection = new DbConnect().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                customers.add(new String[]{
                        resultSet.getString("customerNumber"),
                        resultSet.getString("customerName"),
                        resultSet.getString("contactLastName"),
                        resultSet.getString("contactFirstName"),
                        resultSet.getString("phone"),
                        resultSet.getString("addressLine1"),
                        resultSet.getString("addressLine2"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("country"),
                        resultSet.getString("salesRepEmployeeNumber"),
                        resultSet.getString("creditLimit")
                });
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return customers;
    }

    // Search for customers by name
    public List<String[]> searchCustomersByName(String customerName) {
        List<String[]> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE customerName LIKE ?";
        try (Connection connection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + customerName + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    searchResults.add(new String[]{
                            resultSet.getString("customerNumber"),
                            resultSet.getString("customerName"),
                            resultSet.getString("contactLastName"),
                            resultSet.getString("contactFirstName"),
                            resultSet.getString("phone"),
                            resultSet.getString("addressLine1"),
                            resultSet.getString("addressLine2"),
                            resultSet.getString("city"),
                            resultSet.getString("state"),
                            resultSet.getString("postalCode"),
                            resultSet.getString("country"),
                            resultSet.getString("salesRepEmployeeNumber"),
                            resultSet.getString("creditLimit")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }


    // Search for customers by number
    public List<String[]> searchCustomersByNumber(String customerNumber) {
        List<String[]> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE customerNumber LIKE ?";
        try (Connection connection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerNumber + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    searchResults.add(new String[]{
                            resultSet.getString("customerNumber"),
                            resultSet.getString("customerName"),
                            resultSet.getString("contactLastName"),
                            resultSet.getString("contactFirstName"),
                            resultSet.getString("phone"),
                            resultSet.getString("addressLine1"),
                            resultSet.getString("addressLine2"),
                            resultSet.getString("city"),
                            resultSet.getString("state"),
                            resultSet.getString("postalCode"),
                            resultSet.getString("country"),
                            resultSet.getString("salesRepEmployeeNumber"),
                            resultSet.getString("creditLimit")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
/**
 * Method to find customer records by customer number in the database.
 *
 * @param customerNumber The unique identifier for the customer to locate.
 * @return A collection of arrays, where each array holds the information of a customer corresponding to the given identifier.
 */
public List<String[]> searchCustomersNumber(String customerNumber) {
    List<String[]> searchResults = new ArrayList<>();
    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // Execute prepared statement to search for customers after the given number
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("SELECT * FROM customers WHERE customerNumber = ?");
        preparedStatement.setString(1, customerNumber);
        ResultSet myRs = preparedStatement.executeQuery();

        // Process the retrieved data and populate the 'searchResults' list
        while (myRs.next()) {
            String[] customer = new String[]{
                   myRs.getString("customerNumber"),
myRs.getString("customerName"),
myRs.getString("contactLastName"),
myRs.getString("contactFirstName"),
myRs.getString("phone"),
myRs.getString("addressLine1"),
myRs.getString("addressLine2"),
myRs.getString("city"),
myRs.getString("state"),
myRs.getString("postalCode"),
myRs.getString("country"),
myRs.getString("salesRepEmployeeNumber"),
myRs.getString("creditLimit"),
            };

            searchResults.add(customer);
        }
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
    }

    return searchResults;
}
// HEr
/**
 * Method to remove a customer record from the database.
 *
 * @param customerNumber The identifier of the customer to be removed.
 * @return true if the customer was successfully deleted, false if the deletion failed.
 */



/**
 * Method to register a new customer in the database.
 *
 * @param customerNumber          The unique customer number.
 * @param customerName            The name of the customer.
 * @param contactLastName         The last name of the customer's contact person.
 * @param contactFirstName        The first name of the customer's contact person.
 * @param phone                   The phone number of the customer.
 * @param addressLine1            The primary address line of the customer.
 * @param addressLine2            The secondary address line of the customer.
 * @param city                    The city of the customer's address.
 * @param state                   The state of the customer's address.
 * @param postalCode              The postal code of the customer's address.
 * @param country                 The country of the customer's address.
 * @param salesRepEmployeeNumber  The employee number of the sales representative managing this customer.
 * @param creditLimit             The credit limit assigned to the customer.
 * @return true if the customer was successfully added, false if the addition failed.
 */
 // Method to add a new customer
 public boolean addCustomer(String customernumber ,String customerName, String contactLastName, 
 String contactFirstName, String phone, String addressLine1, 
 String addressLine2, String city, String state, String postalCode, 
 String country, String salesRepEmployeeNumber, String creditLimit) {
    String sql = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = new DbConnect().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    
        preparedStatement.setString(1, customernumber); // Set the generated customer number here
        preparedStatement.setString(2, customerName);
        preparedStatement.setString(3, contactLastName);
        preparedStatement.setString(4, contactFirstName);
        preparedStatement.setString(5, phone);
        preparedStatement.setString(6, addressLine1);
        preparedStatement.setString(7, addressLine2);
        preparedStatement.setString(8, city);
        preparedStatement.setString(9, state);
        preparedStatement.setString(10, postalCode);
        preparedStatement.setString(11, country);
        preparedStatement.setString(12, salesRepEmployeeNumber);
        preparedStatement.setString(13, creditLimit); // Note the index change here
    
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException ex) {
        System.err.println("SQL Exception: " + ex.getMessage());
        return false;
    } catch (ClassNotFoundException ex) {
        System.err.println("Class Not Found Exception: " + ex.getMessage());
        return false;
    }
}

// Method to update a customer record
public boolean updateCustomer(String customerNumber, String customerName, String contactLastName, 
    String contactFirstName, String phone, String addressLine1, 
    String addressLine2, String city, String state, String postalCode, 
    String country, String salesRepEmployeeNumber, String creditLimit) {
String sql = "UPDATE customers SET customerName = COALESCE(?, customerName), " +
"contactLastName = COALESCE(?, contactLastName), " +
"contactFirstName = COALESCE(?, contactFirstName), " +
"phone = COALESCE(?, phone), " +
"addressLine1 = COALESCE(?, addressLine1), " +
"addressLine2 = COALESCE(?, addressLine2), " +
"city = COALESCE(?, city), " +
"state = COALESCE(?, state), " +
"postalCode = COALESCE(?, postalCode), " +
"country = COALESCE(?, country), " +
"salesRepEmployeeNumber = COALESCE(?, salesRepEmployeeNumber), " +
"creditLimit = COALESCE(?, creditLimit) WHERE customerNumber = ?";

try (Connection connection = new DbConnect().getConnection();
PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

preparedStatement.setString(1, isEmpty(customerName) ? null : customerName);
preparedStatement.setString(2, isEmpty(contactLastName) ? null : contactLastName);
preparedStatement.setString(3, isEmpty(contactFirstName) ? null : contactFirstName);
preparedStatement.setString(4, isEmpty(phone) ? null : phone);
preparedStatement.setString(5, isEmpty(addressLine1) ? null : addressLine1);
preparedStatement.setString(6, isEmpty(addressLine2) ? null : addressLine2);
preparedStatement.setString(7, isEmpty(city) ? null : city);
preparedStatement.setString(8, isEmpty(state) ? null : state);
preparedStatement.setString(9, isEmpty(postalCode) ? null : postalCode);
preparedStatement.setString(10, isEmpty(country) ? null : country);
preparedStatement.setString(11, isEmpty(salesRepEmployeeNumber) ? null : salesRepEmployeeNumber);
preparedStatement.setString(12, isEmpty(creditLimit) ? null : creditLimit);
preparedStatement.setString(13, customerNumber);

int rowsAffected = preparedStatement.executeUpdate();
return rowsAffected > 0;
} catch (SQLException | ClassNotFoundException ex) {
ex.printStackTrace();
return false;
}
}

// Any additional methods or inner classes can be added here
public boolean deleteCustomer(String customerNumber) {
    String sql = "DELETE FROM customers WHERE customerNumber = ?";
    try (Connection connection = new DbConnect().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, customerNumber);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLIntegrityConstraintViolationException e) {
        System.err.println("Cannot delete customer because they have existing orders.");
        return false;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
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