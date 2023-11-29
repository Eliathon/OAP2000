//Created by Jpohnny

package com.oap200.app.controllers;

import com.oap200.app.models.CustomerDAO;
import com.oap200.app.views.CustomerManagementPanel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

public class CustomerController {

    private CustomerDAO customerDAO;
    private CustomerManagementPanel customerManagementPanel;

    public CustomerController(CustomerDAO customerDAO, CustomerManagementPanel customerManagementPanel) {
        this.customerDAO = customerDAO;
        this.customerManagementPanel = customerManagementPanel;
    }

    public void handleSearchCustomer(String customerName) {
        List<String[]> searchResults = CustomerDAO.searchCustomer(customerName);
        customerManagementPanel.displayCustomers(searchResults);
    }

     public String getLatestCustomerNumber() {
        try {
            // Assuming you have a method in your CustomerDAO to get the latest customer number
            return customerDAO.getLatestCustomerNumber();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception based on your requirements
            return ""; // You might want to throw an exception or return a default value
        }
    }

    public void handleViewAllCustomers() {
        List<String[]> allCustomers = customerDAO.fetchCustomers();
        customerManagementPanel.displayCustomers(allCustomers);
    }

    public boolean handleDeleteCustomer(String customerCode) {
        boolean deletionSuccessful = customerDAO.deleteCustomer(customerCode);
        if (deletionSuccessful) {
            return true;
        } else {
            return false;
        }
    }

    public boolean handleAddCustomer(
            String customerNumber, String customerName, String contactLastName, String contactFirstName,
            String phone, String addressLine1, String addressLine2, String city, String state,
            String postalCode, String country, String salesRepEmployeeNumberText, String creditLimitText) {
        try {
            int salesRepEmployeeNumber = Integer.parseInt(salesRepEmployeeNumberText);
            BigDecimal creditLimit = new BigDecimal(creditLimitText);

            // Assuming you have a method in your DAO to add a customer
            return customerDAO.addCustomer(
                    customerNumber, customerName, contactLastName, contactFirstName, phone,
                    addressLine1, addressLine2, city, state, postalCode, country,
                    salesRepEmployeeNumber, creditLimit);
        } catch (NumberFormatException | ArithmeticException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(customerManagementPanel, "Error converting numbers.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public CustomerDAO getCustomerDAO() {
        return this.customerDAO;
    }

    public boolean handleUpdateCustomer(
            String customerName, String contactLastName, String contactFirstName, String addressLine1,
            String addressLine2, String city, String state, String postalCode, String country,
            String salesRepEmployeeNumberText, String creditLimitText, String updatePhone) {
        try {
            int salesRepEmployeeNumber = Integer.parseInt(salesRepEmployeeNumberText);
            BigDecimal creditLimit = new BigDecimal(creditLimitText);

            // Assuming there's a method in your DAO to update a customer
            return customerDAO.UpdateCustomer(
                    customerName, contactLastName, contactFirstName, addressLine1, addressLine2,
                    city, state, postalCode, country, salesRepEmployeeNumber, creditLimit);
        } catch (NumberFormatException | ArithmeticException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(customerManagementPanel, "Error converting numbers.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}