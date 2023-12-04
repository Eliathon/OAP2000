
/** CustomerController Class
 *  Customer is a class that acts as a controller for managing customers. It handles interactions
 * between the CustomerDAO (data access object) and the CustomerManagementPanel (view).
 * @author Johnny
 * @version 1.0
 * @since 2023-04-12
 */

package com.oap200.app.controllers;

import com.oap200.app.models.CustomerDAO;
import com.oap200.app.views.CustomerManagementPanel;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    // Method for handling the display of customers
    private CustomerDAO customerDAO;
    private CustomerManagementPanel customerManagementPanel;

     /** Constructor for Customer Controller     *
     * @param customerDAO            Data access object for customer-related operations.
     * @param customerManagementPanel Panel for managing the display of customers.
     */
     public CustomerController(CustomerDAO customerDAO, CustomerManagementPanel customerManagementPanel) {
     this.customerDAO = customerDAO;
     this.customerManagementPanel = customerManagementPanel;
    }
    /**
     * Get details of a customer based on the customer number.
     *
     * @param customerNumber The customer number to retrieve details for.
     * @return An array of customer details, or null if not found or an exception occurs.
     */
        public String[] getCustomerDetails(String customerNumber) {
        try {
            return customerDAO.getCustomerDetails(customerNumber);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Handle searching for customers by name.
     *
     * @param customerName The name to search for.
     * @return List of search results, or an empty list if none found.
     */
    public List<String[]> handleSearchCustomersByNumber(String customerNumber) {
        List<String[]> searchResults = customerDAO.searchCustomersByNumber(customerNumber);
        if (!searchResults.isEmpty()) {
            customerManagementPanel.displayCustomers(searchResults);
        } else {
            // Optionally, you could handle the case where no results are found,
            // for example, by displaying a message or clearing the table.
            customerManagementPanel.clearCustomersDisplay();
        }
        return searchResults;
    }
     /**
     * Handle searching for customers by number.
     *
     * @param customerNumber The customer number to search for.
     * @return List  the search results, or an empty list if none found.
     */
    public List<String[]> handleSearchCustomersNumber(String customerNumber) {
        List<String[]> searchResults = customerDAO.searchCustomersByNumber(customerNumber);
        if (!searchResults.isEmpty()) {
            customerManagementPanel.displayCustomers(searchResults);
        }
        return searchResults;
    }
    /**
     * Handle displaying all employees.
     */     

    public void handleViewAllCustomers() {
        List<String[]> allCustomers = customerDAO.fetchCustomers();
        customerManagementPanel.displayCustomers(allCustomers);
    }

    public boolean handleDeleteCustomer(String customerNumber) {
        boolean deletionSuccessful = customerDAO.deleteCustomer(customerNumber);
        if (deletionSuccessful) {
            handleViewAllCustomers();
            return true;
        } else {
            return false;
        }
    }
    public List<String[]> handleSearchCustomers(String customerName) {
        List<String[]> searchResults = customerDAO.searchCustomersByName(customerName);
        if (!searchResults.isEmpty()) {
            customerManagementPanel.displayCustomers(searchResults);
        } else {
            customerManagementPanel.clearCustomersDisplay();
        }
        return searchResults;
    }

/**
 * Handle registering a new customer.
 *
 * @param customerNumber         The unique identifier for the new customer.
 * @param customerName           The name of the new customer.
 * @param contactLastName        The last name of the primary contact for the new customer.
 * @param contactFirstName       The first name of the primary contact for the new customer.
 * @param phone                  The phone number for the new customer.
 * @param addressLine1           The primary address line for the new customer.
 * @param addressLine2           The secondary address line for the new customer (optional).
 * @param city                   The city of residence for the new customer.
 * @param state                  The state or region for the new customer's address.
 * @param postalCode             The postal code for the new customer's address.
 * @param country                The country of residence for the new customer.
 * @param salesRepEmployeeNumber The employee number of the sales representative managing this customer.
 * @param creditLimit            The credit limit set for the new customer.
 * @return True if the registration is successful, false otherwise.
 */

 public boolean handleAddCustomer(String customerName, String contactLastName, 
                                     String contactFirstName, String phone, String addressLine1, 
                                     String addressLine2, String city, String state, String postalCode, 
                                     String country, String salesRepEmployeeNumber, String creditLimit) {
        try {
            String customerNumber = customerDAO.getNextAvailableCustomerNumber();
            boolean isAdded = customerDAO.addCustomer(customerNumber, customerName, contactLastName,
                                                      contactFirstName, phone, addressLine1,
                                                      addressLine2, city, state, postalCode,
                                                      country, salesRepEmployeeNumber, creditLimit);
            if (isAdded) {
                handleViewAllCustomers();
            }
            return isAdded;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CustomerDAO getCustomerDAO() {
        return this.customerDAO;
    }

    public boolean handleUpdateCustomer(String customerNumber, String customerName, String contactLastName, 
                                        String contactFirstName, String phone, String addressLine1, 
                                        String addressLine2, String city, String state, String postalCode, 
                                        String country, String salesRepEmployeeNumber, String creditLimit) {
        if (customerNumber == null || customerNumber.trim().isEmpty()) {
            System.out.println("Invalid input for Customer Number.");
            return false;
        }

        boolean isUpdated = customerDAO.updateCustomer(customerNumber, customerName, contactLastName, 
                                                       contactFirstName, phone, addressLine1, 
                                                       addressLine2, city, state, postalCode, 
                                                       country, salesRepEmployeeNumber, creditLimit);
        if (isUpdated) {
            handleViewAllCustomers();
        }
        return isUpdated;
    }
} 

