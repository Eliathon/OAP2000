// Created by Johnny
/*
 The Customer Management class is responsible for providing a user interface
 for managing customers. It includes features to view, add, update, and delete
customers, as well as search functionality.
 */

package com.oap200.app.views;

import com.oap200.app.models.CustomerDAO;
import com.oap200.app.controllers.CustomerController;
import com.oap200.app.utils.ButtonBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CustomerManagementPanel extends JPanel {
    // Declaration of class fields
    private JTable customerTable;
    private JTextField searchByNumberField;
    private JTextField searchNumberField, searchNameField;
    private JTextField customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit;
    private JTextField updatePhone, updateAddressLine1, updateAddressLine2, updateCity, updateState, updatePostalCode, updateCountry, updateSalesRepEmployeeNumber, updateCreditLimit;
    private CustomerController customerController;
    private JTextField updateContactLastName;
    private JTextField updateContactFirstName;

    public CustomerManagementPanel(JFrame parentFrame) {
        customerController = new CustomerController(new CustomerDAO(), this);
        initializeFields();
        searchByNumberField = new JTextField(10);

        setLayout(new BorderLayout());

        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            Window window = SwingUtilities.getWindowAncestor(CustomerManagementPanel.this);
            if (window != null) {
                window.dispose();
            }
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            Window currentWindow = SwingUtilities.getWindowAncestor(CustomerManagementPanel.this);
            if (currentWindow != null) {
                currentWindow.dispose();
            }

            if (parentFrame != null) {
                parentFrame.dispose();
            }

            openLoginPanel();
        });

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View customer", null, createViewPanel(), "Click to view");
        tabbedPane.addTab("Add customer", null, createAddPanel(), "Click to add");
        tabbedPane.addTab("Update customer", null, createUpdatePanel(), "Click to Update");
        tabbedPane.addTab("Delete customer", null, createDeletePanel(), "Click to Delete");

        add(createButtonPanel(backButton, logoutButton), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void initializeFields() {
        // Initialize text fields
        searchNumberField = new JTextField(10);
        searchNameField = new JTextField(10);
        customerNumber = new JTextField(10);
        customerName = new JTextField(10);
        contactLastName = new JTextField(10);
        contactFirstName = new JTextField(10);
        phone = new JTextField(10);
        addressLine1 = new JTextField(10);
        addressLine2 = new JTextField(10);
        city = new JTextField(10);
        state = new JTextField(10);
        postalCode = new JTextField(10);
        country = new JTextField(10);
        salesRepEmployeeNumber = new JTextField(10);
        creditLimit = new JTextField(10);
        
		
		
      
       
  

        customerTable = new JTable(new DefaultTableModel());


    }

    private JPanel createButtonPanel(JButton backButton, JButton logoutButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        return buttonPanel;
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Create the search panel for employee customer number with FlowLayout
        JPanel searchByNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchByNumberPanel.add(new JLabel("Search by Number:"));
        searchByNumberPanel.add(searchByNumberField); // Add the new search field
        JButton searchNumberButton = ButtonBuilder.createSearchNumberButton(() -> searchCustomersByNumber());
        searchByNumberPanel.add(searchNumberButton);
    
        // Create the search panel for  name with FlowLayout
        JPanel searchByNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchByNamePanel.add(new JLabel("Search by Name:"));
        searchByNamePanel.add(searchNameField);
        JButton searchNameButton = ButtonBuilder.createSearchButton(() -> searchCustomersName());
        searchByNamePanel.add(searchNameButton);
    
        // Combine both search panels into a single panel with BoxLayout
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
        searchPanel.add(searchByNumberPanel);
        searchPanel.add(searchByNamePanel);
    
        // Add the search panel to the top of the view panel
        panel.add(searchPanel, BorderLayout.NORTH);
    
        // The rest of your method to add the table view
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton viewAllButton = ButtonBuilder.createViewButton(() -> customerController.handleViewAllCustomers());
        JPanel viewAllButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewAllButtonPanel.add(viewAllButton);
        panel.add(viewAllButtonPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    
    // Sets up a Grid Panel with // 13 rows for 13 fields, 2 columns for labels and text fields 
    private JPanel createAddPanel() {JPanel panel = new JPanel(new BorderLayout());
        JButton addButton = ButtonBuilder.createAddButton(() -> addCustomer());
        // Create the "Add" button and add ActionListener

addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // This code will execute when the "Add" button is clicked
        addCustomer(); // Call the method to add a new customer
    }
}); 
       
       
        JPanel addPanel = new JPanel(new GridLayout(13, 2));  
        
        // Customer Name
        addPanel.add(new JLabel("Customer Name:"));
        customerName = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(customerName);

        // Contact First Name
        addPanel.add(new JLabel("Contact last Name:"));
        contactLastName = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(contactLastName);
        
        // Contact First Name
        addPanel.add(new JLabel("Contact first Name:"));
        contactFirstName = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(contactFirstName);
        
        // Phone Number
        addPanel.add(new JLabel("Phone Number:"));
        phone = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(phone);
        
        // Address Line 1
        addPanel.add(new JLabel("Address Line 1:"));
        addressLine1 = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(addressLine1);
        
        // Address Line 2
        addPanel.add(new JLabel("Address Line 2 :"));
        addressLine2 = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(addressLine2);
        
        // City
        addPanel.add(new JLabel("City:"));
        city = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(city);
        
        // State
        addPanel.add(new JLabel("State:"));
        state = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(state);
        
        // Postal Code
        addPanel.add(new JLabel("Postal Code:"));
        postalCode = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(postalCode);
        
        // Country
        addPanel.add(new JLabel("Country:"));
        country = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(country);
        
        // Sales rep Employee Number
        addPanel.add(new JLabel("Sales rep Employee Number:"));
        salesRepEmployeeNumber = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(salesRepEmployeeNumber);
        
        // Credit Limit
        addPanel.add(new JLabel("Credit Limit:"));
        creditLimit = new JTextField(10); // Reduced size to 10 columns
        addPanel.add(creditLimit);
        
        panel.add(addPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
        }
   // Sets up a Grid Panel with // 11 rows for 11 fields, 2 columns for labels and text fields 
   private JPanel createUpdatePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    JButton addButton = ButtonBuilder.createAddButton(() ->addCustomer());
    JPanel addPanel = new JPanel(new GridLayout(11, 2));  // Adjusted to 11 rows for 11 fields

        panel.add(addPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
        }
 

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> deleteCustomer());
        JPanel deletePanel = new JPanel(new GridLayout(1, 2));
        deletePanel.add(new JLabel("Customer Number:"));
        deletePanel.add(searchNumberField);
        panel.add(deletePanel, BorderLayout.NORTH);
        panel.add(deleteButton, BorderLayout.SOUTH);
        return panel;
    }


    public void refreshUI() {
        revalidate();
        repaint();
    }

    private void openLoginPanel() {
        JFrame loginFrame = new JFrame("Login");
        LoginPanel loginPanel = new LoginPanel();
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    private void searchCustomersName() {
        String customerName = searchNameField.getText().trim();
    
        if (!customerName.isEmpty()) {
            customerController.handleSearchCustomers(customerName);
        } else {
            // Handle the case where the name field is empty (e.g., show a message)
            JOptionPane.showMessageDialog(this, "Please enter an customer name to search.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void clearCustomersDisplay() {
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        model.setRowCount(0); // This will clear all the rows in the table
    }
    
    private void searchCustomersByNumber() {
        String customerNumber = searchByNumberField.getText().trim(); // Use the new search field
    
        if (!customerNumber.isEmpty()) {
            customerController.handleSearchCustomersByNumber(customerNumber);
        } else {
            // Handle the case where the number field is empty (e.g., show a message)
            JOptionPane.showMessageDialog(this, "Please enter an customer number to search.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCustomer() {
        String cName = customerName.getText().trim();
        String cLastName = contactLastName.getText().trim();
        String cFirstName = contactFirstName.getText().trim();
        String phoneText = phone.getText().trim();
        String aLine1 = addressLine1.getText().trim();
        String aLine2 = addressLine2.getText().trim();
        String cityText = city.getText().trim();
        String stateText = state.getText().trim();
        String pCode = postalCode.getText().trim();
        String countryText = country.getText().trim();
        String sRepEmployeeNumber = salesRepEmployeeNumber.getText().trim();
        String cLimit = creditLimit.getText().trim();
    
        // Validate last name
        if (cName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Customer Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate first name
        if (cLastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate extension
        if (cFirstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email
        if (phoneText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone number cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

         // Validate last name
        if (aLine1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adress line1 cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate first name
        if (aLine2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adress line2 cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate extension
        if (cityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "City text cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email
        if (stateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "State text cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
         // Validate first name
        if (pCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Postal code cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate extension
        if (countryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Country text cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email
        if (sRepEmployeeNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "sRepEmployeeNumber cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate email
        if (cLimit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Credit limit cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Validate office code
        try {
            Integer.parseInt(phoneText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Phone number must be a number, and not empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
       
        boolean success = customerController.handleAddCustomer(cName, cLastName, cFirstName, phoneText, aLine1, aLine2, cityText, stateText, pCode, 
        countryText, sRepEmployeeNumber, cLimit);

        if (success) {
        String generatedCustomerNumber = customerController.getCustomerDAO().getGeneratedCustomerNumber();
        String cNumber = generatedCustomerNumber;
        System.out.println("1:" + cNumber);
        System.out.println("Cname:" + cName);
        System.out.println("3:" + cLastName);
        System.out.println("4:" + cFirstName);
        System.out.println("5:" + phoneText);
        System.out.println("6:" + aLine1);
        System.out.println("7:" + aLine2);
        System.out.println("8:" + cityText);

        System.out.println("9:" + stateText);
        System.out.println("10:" + pCode);
        System.out.println("11:" + countryText);
        System.out.println("12:" + sRepEmployeeNumber);
        System.out.println("13:" + cLimit);

        String customerInfoMessage = "Employee added successfully with inputs:\n\n" +
                "Customer Number: " + cNumber + "\n" +
                "Customer Name: " + cName + "\n" +
                "Last Name: " + cLastName + "\n" +
                "First Name: " + cFirstName + "\n" +
                "Phone: " + phoneText + "\n" +
                "Adress Line 1: " + aLine1 + "\n" +
                "Adress Line 2: " + aLine2 + "\n" +
                "City Text: " + cityText + "\n" +
                "State Text: " + stateText + "\n" +
                "Postal code: " + pCode + "\n" +
                "Country Text: " + countryText + "\n" +
                "sRepEmployeeNumber: " + sRepEmployeeNumber + "\n" +
                "Credit Limit: " + cLimit + "\n" ;
                

        JOptionPane.showMessageDialog(this, customerInfoMessage, "Addition completed", JOptionPane.INFORMATION_MESSAGE);
    
           
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add customer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Clear input fields
    private void clearInputFields() {
        customerNumber.setText("");
        customerName.setText("");
        contactLastName.setText("");
      //  contactFirstName.setText("");
        phone.setText("");
        addressLine1.setText("");
        addressLine2.setText("");
        city.setText("");
        state.setText("");
        postalCode.setText("");
        country.setText("");
        salesRepEmployeeNumber.setText("");
        creditLimit.setText("");
    }
            private void updateCustomer() {
                // Retrieve values from text fields
                String cNum = customerNumber.getText().trim(); // Replace 'customerNumber' with your actual field name for customer number
                String cLastName = updateContactLastName.getText().trim();
                String cFirstName = updateContactFirstName.getText().trim();
                String phoneText = updatePhone.getText().trim();
                String aLine1 = updateAddressLine1.getText().trim();
                String aLine2 = updateAddressLine2.getText().trim();
                String cityText = updateCity.getText().trim();
                String stateText = updateState.getText().trim();
                String pCode = updatePostalCode.getText().trim();
                String countryText = updateCountry.getText().trim();
                String sRepEmployeeNumber = updateSalesRepEmployeeNumber.getText().trim();
                String cLimit = updateCreditLimit.getText().trim();
            
                // Validate Contact Last Name
                if (cLastName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Contact Last Name cannot be empty. Type in the customer's contacts Last Name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Contact First Name
                if (cFirstName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Contact First Name cannot be empty. Type in the customer's contacts First Name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Phone
                if (phoneText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Phone cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Address Line 1
                if (aLine1.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Address Line 1 cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Address Line 2 (if required)
                if (aLine2.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Address Line 2 cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate City
                if (cityText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "City cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate State
                if (stateText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "State cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Postal Code
                if (pCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Postal Code cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Country
                if (countryText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Country cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Sales Rep Employee Number
                if (sRepEmployeeNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Sales Representative Employee Number cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Validate Credit Limit
                if (cLimit.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Credit Limit cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Create an array of customer details
                String[] customerDetails = new String[] {
                    cNum, cLastName, cFirstName, phoneText, aLine1, aLine2, cityText, stateText, pCode, countryText, sRepEmployeeNumber, cLimit
                };
            
                // Call the method to update customer details
                // Now call handleUpdateCustomer with individual elements of the array
customerController.handleUpdateCustomer(
    customerDetails[0], // customerNumber
    customerDetails[1], // customerName
    customerDetails[2], // contactLastName
    customerDetails[3], // contactFirstName
    customerDetails[4], // phone
    customerDetails[5], // addressLine1
    customerDetails[6], // addressLine2
    customerDetails[7], // city
    customerDetails[8], // state
    customerDetails[9], // postalCode
    customerDetails[10], // country
    customerDetails[11], // salesRepEmployeeNumber
    customerDetails[12]  // creditLimit
);
            
                // You can add any follow-up action here, like a confirmation message
                JOptionPane.showMessageDialog(this, "Customer update attempted", "Update Attempt", JOptionPane.INFORMATION_MESSAGE);
            }
    
            private void deleteCustomer() {
                String customerNum = searchNumberField.getText();
            
                try {
                    String[] customerDetails = customerController.getCustomerDAO().getCustomerDetails(customerNum);
            
                    // Assuming you have some condition or method to check if the customer is deleted
                    if (isCustomerDeleted(customerNum)) {
                        String message = String.format("Customer with these details has been deleted successfully from the system: \n Customer Number: %s \n Customer Name: %s \n Contact Last Name: %s \n Contact First Name: %s \n Contact Phone: %s \n Contact Address Line 1: %s \n Contact Address Line 2: %s \n Contact City: %s \n Contact State: %s \n Contact Postal Code: %s \n Contact Country: %s \n Sales Rep Employee: %s \n Credit Limit: %s", 
                                                       customerDetails[0], // Customer Number
                                                       customerDetails[1], // Customer Name
                                                       customerDetails[2], // Contact Last Name
                                                       customerDetails[3], // Contact First Name
                                                       customerDetails[4], // Contact Phone
                                                       customerDetails[5], // Contact Address Line 1
                                                       customerDetails[6], // Contact Address Line 2
                                                       customerDetails[7], // Contact City
                                                       customerDetails[8], // Contact State
                                                       customerDetails[9], // Contact Postal Code
                                                       customerDetails[10], // Contact Country
                                                       customerDetails[11], // Sales Rep Employee
                                                       customerDetails[12] // Credit Limit
                        );
                        JOptionPane.showMessageDialog(this, message, "Customer Deleted", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete customer, customer not found or because they have existing orders.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    // Handle the exception
                    JOptionPane.showMessageDialog(this, "Error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
           public boolean isCustomerDeleted(String customerNum) {
    boolean deletionSuccessful = customerController.handleDeleteCustomer(customerNum);
    if (deletionSuccessful) {
        // Handle successful deletion, e.g., refresh the customer list or show a confirmation message
        System.out.println("Customer successfully deleted");
        return true; // Return true to indicate successful deletion
    } else {
        System.out.println("Failed to delete customer");
        return false; // Return false to indicate failed deletion
    }
}



    public void displayCustomers(List<String[]> customerList) {
        // Define the column names
        String[] columnNames = { "Customer Number", "Customer Name", "Contact Last Name", "Contact First Name", "Phone", "Address Line 1", "Address Line 2"
       ,"City", " State" ,   " Postal Code" , "Country" , " Sales Rep Employee" ," Credit Limit "};

        // Create a new table model with column names and no rows iually
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        // Populate the model with data
        for (String[] row : customerList) {
            model.addRow(row);
        }

        // Set the model to the customerTable
        customerTable.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Customer Management");
            frame.setContentPane(new CustomerManagementPanel(frame));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
