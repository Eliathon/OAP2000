package com.oap200.app.views;

import com.oap200.app.models.CustomerDAO;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.controllers.CustomerController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerManagementPanel extends JPanel {

    private JTable customersTable;
    private JTextField searchTextField, searchCodeField;
    private JTextField customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1,
            addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit;
    private JTextField updatecustomerName;
    private JTextField updatecontactLastName;
    private JTextField updatecontactFirstName;
    private JTextField updateadressLine1;
    private JTextField updateadressLine2;
    private JTextField updatecity;
    private JTextField updatestate;
    private JTextField updatePostalcode;
    private JTextField updatecountry;
    private JTextField updatesalesRepEmployeeNumber;
    private JTextField updatecreditLimit;
    private JTextField updatePhone;
    private JButton viewButton, searchButton, addButton, updateButton, deleteButton;
    private CustomerController customerController;

    public CustomerManagementPanel(JFrame parentFrame) {
        initializeFields();
        customerController = new CustomerController(new CustomerDAO(), this);

        setLayout(new BorderLayout());

        JButton backButton = ButtonBuilder.createBlueBackButton(() -> parentFrame.dispose());
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            System.exit(0); // Exit the application
            openLoginPanel();
        });

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(createViewSearchButtonPanel(viewButton, searchButton), BorderLayout.SOUTH);
        tabbedPane.addTab("View Customers", null, panel1, "Click to view");

        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanelAdd(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Customers", null, panel2, "Click to add");

        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanelUpdate(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Customers", null, panel3, "Click to Update");

        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanelDelete(panel4);
        panel4.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Customers", null, panel4, "Click to Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add mainPanel to CustomerManagementPanel
        add(mainPanel, BorderLayout.CENTER);

    }

    private JPanel createViewSearchButtonPanel(JButton viewButton, JButton searchButton) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(viewButton);
        panel.add(searchButton);
        return panel;
    }

    private void initializeFields() {
        // Initialize buttons
        viewButton = new JButton("View Customers");
        viewButton.addActionListener(e -> viewCustomers());
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchCustomers());
        addButton = new JButton("Add");
        addButton.addActionListener(e -> addCustomer());
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateCustomer());
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCustomer());

        // Initialize text fields
        searchTextField = new JTextField(10);
        searchCodeField = new JTextField(10);
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

        updatecustomerName = new JTextField(10);
        updatecontactLastName = new JTextField(10);
        updatecontactFirstName = new JTextField(10);
        updatePhone = new JTextField(10);
        updateadressLine1 = new JTextField(10);
        updateadressLine2 = new JTextField(10);
        updatecity = new JTextField(10);
        updatestate = new JTextField(10);
        updatePostalcode = new JTextField(10);
        updatecountry = new JTextField(10);
        updatesalesRepEmployeeNumber = new JTextField(10);
        updatecreditLimit = new JTextField(10);

        String[] columnNames = {
                "Customer Number", "Customer Name", "Contact Last Name", "Contact First Name",
                "Phone", "Address Line 1", "Address Line 2", "City", "State", "Postal Code",
                "Country", "Sales Rep Employee Number", "Credit Limit"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        customersTable = new JTable(tableModel);

    }

    private void viewCustomers() {
        // This method should ask the controller to update the table model
        customerController.handleViewAllCustomers();
    }

    private void searchCustomers() {
        String customerName = searchTextField.getText();
        customerController.handleSearchCustomer(customerName);
    }

    private void deleteCustomer() {
        String customerNumber = searchCodeField.getText();
        boolean deletionSuccessful = customerController.handleDeleteCustomer(customerNumber);
        if (deletionSuccessful) {
            JOptionPane.showMessageDialog(this, "Customer deleted successfully.", "Deletion completed",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting customer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCustomer() {
        // Fetch the latest customer number and increment it
        String latestCustomerNumber = customerController.getLatestCustomerNumber();
        String newCustomerNumber = generateNextCustomerNumber(latestCustomerNumber);
    
        boolean additionSuccessful = customerController.handleAddCustomer(
                newCustomerNumber,
                customerName.getText(),
                contactLastName.getText(),
                contactFirstName.getText(),
                phone.getText(),
                addressLine1.getText(),
                addressLine2.getText(),
                city.getText(),
                state.getText(),
                postalCode.getText(),
                country.getText(),
                salesRepEmployeeNumber.getText(),
                creditLimit.getText());
    
        if (additionSuccessful) {
            JOptionPane.showMessageDialog(this, "New Customer added successfully.", "Addition completed",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error adding customer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper method to generate the next customer number
    private String generateNextCustomerNumber(String latestCustomerNumber) {
        try {
            // Assuming your customer numbers are numeric
            int latestNumber = Integer.parseInt(latestCustomerNumber);
            int nextNumber = latestNumber + 1;
            return String.valueOf(nextNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception based on your requirements
            return ""; // You might want to throw an exception or return a default value
        }
    }
    
    private void openLoginPanel() {
        JFrame loginFrame = new JFrame("Login");
        LoginPanel loginPanel = new LoginPanel();
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    private void updateCustomer() {
        // Get the updated information from text fields
        String updatedCustomerName = updatecustomerName.getText();
        String updatedContactLastName = updatecontactLastName.getText();
        String updatedContactFirstName = updatecontactFirstName.getText();
        String updatedAddressLine1 = updateadressLine1.getText();
        String updatedAddressLine2 = updateadressLine2.getText();
        String updatedCity = updatecity.getText();
        String updatedState = updatestate.getText();
        String updatedPostalCode = updatePostalcode.getText();
        String updatedCountry = updatecountry.getText();
        String updatedSalesRepEmployeeNumber = updatesalesRepEmployeeNumber.getText();
        String updatedCreditLimit = updatecreditLimit.getText();
        String updatedPhone = updatePhone.getText();

        // Call the update method in the controller
        boolean updateSuccessful = customerController.handleUpdateCustomer(
                updatedCustomerName, updatedContactLastName, updatedContactFirstName,
                updatedAddressLine1, updatedAddressLine2, updatedCity, updatedState,
                updatedPostalCode, updatedCountry, updatedSalesRepEmployeeNumber, updatedCreditLimit, updatedPhone);

        // Show a message based on the result
        if (updateSuccessful) {
            JOptionPane.showMessageDialog(this, "Customer updated successfully.", "Update completed",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error updating customer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addComponentsToPanelAdd(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(12, 1)); // Adjusted for the number of customer fields
        JPanel fieldPanel = new JPanel(new GridLayout(12, 1)); // Adjusted for the number of customer fields

        // Adding customer-related labels and fields
        labelPanel.add(new JLabel("Customer Number:"));
        fieldPanel.add(customerNumber);
        labelPanel.add(new JLabel("Customer Name:"));
        fieldPanel.add(customerName);
        labelPanel.add(new JLabel("Contact Last Name:"));
        fieldPanel.add(contactLastName);
        labelPanel.add(new JLabel("Contact First Name:"));
        fieldPanel.add(contactFirstName);
        labelPanel.add(new JLabel("Phone:"));
        fieldPanel.add(phone);
        labelPanel.add(new JLabel("Address Line 1:"));
        fieldPanel.add(addressLine1); // Check the spelling, it should be 'addressLine1'
        labelPanel.add(new JLabel("Address Line 2:"));
        fieldPanel.add(addressLine2); // Check the spelling, it should be 'addressLine2'
        labelPanel.add(new JLabel("City:"));
        fieldPanel.add(city);
        labelPanel.add(new JLabel("State:"));
        fieldPanel.add(state);
        labelPanel.add(new JLabel("Postal Code:"));
        fieldPanel.add(postalCode);
        labelPanel.add(new JLabel("Country:"));
        fieldPanel.add(country);
        labelPanel.add(new JLabel("Sales Rep Employee Number:"));
        fieldPanel.add(salesRepEmployeeNumber);
        labelPanel.add(new JLabel("Credit Limit:"));
        fieldPanel.add(creditLimit);

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelUpdate(JPanel panelUpdate) {
        panelUpdate.setLayout(new BorderLayout());

        JPanel inputPanelUpdate = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Update Customer Name:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        updatecustomerName = new JTextField(40);
        inputPanelUpdate.add(updatecustomerName, gbc);

        // Contact Last Name
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Contact Last Name:"), gbc);
        gbc.gridx = 1;
        updatecontactLastName = new JTextField(40);
        inputPanelUpdate.add(updatecontactLastName, gbc);

        // Contact First Name
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Contact First Name:"), gbc);
        gbc.gridx = 1;
        updatecontactFirstName = new JTextField(40);
        inputPanelUpdate.add(updatecontactFirstName, gbc);

        // Address Line 1
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Address Line 1:"), gbc);
        gbc.gridx = 1;
        updateadressLine1 = new JTextField(40);
        inputPanelUpdate.add(updateadressLine1, gbc);

        // Address Line 2
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Address Line 2:"), gbc);
        gbc.gridx = 1;
        updateadressLine2 = new JTextField(40);
        inputPanelUpdate.add(updateadressLine2, gbc);

        // City
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update City:"), gbc);
        gbc.gridx = 1;
        updatecity = new JTextField(40);
        inputPanelUpdate.add(updatecity, gbc);

        // State
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update State:"), gbc);
        gbc.gridx = 1;

        inputPanelUpdate.add(updatestate, gbc);

        // Postal Code
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Postal Code:"), gbc);
        gbc.gridx = 1;
        updatePostalcode = new JTextField(40);
        inputPanelUpdate.add(updatePostalcode, gbc);

        // Country
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Country:"), gbc);
        gbc.gridx = 1;
        updatecountry = new JTextField(40);
        inputPanelUpdate.add(updatecountry, gbc);

        // Sales Rep Employee Number
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Sales Rep Employee Number:"), gbc);
        gbc.gridx = 1;
        updatesalesRepEmployeeNumber = new JTextField(40);
        inputPanelUpdate.add(updatesalesRepEmployeeNumber, gbc);

        // Credit Limit
        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Credit Limit:"), gbc);
        gbc.gridx = 1;
        updatecreditLimit = new JTextField(40);
        inputPanelUpdate.add(updatecreditLimit, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Phone:"), gbc);
        gbc.gridx = 1;
        updatePhone = new JTextField(40);
        inputPanelUpdate.add(updatePhone, gbc);

        // Filler Panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        inputPanelUpdate.add(new JPanel(), gbc);

        // Add the input panel to the panelUpdate
        panelUpdate.add(inputPanelUpdate, BorderLayout.NORTH);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanel.add(new JLabel("Customer name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(searchTextField, gbc);

        panelView.add(inputPanel, BorderLayout.NORTH);

        // Create the scrollPane with the customersTable
        JScrollPane scrollPane = new JScrollPane(customersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
    }

    private void addComponentsToPanelDelete(JPanel panelDelete) {
        panelDelete.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        inputPanel.add(new JLabel("Customer Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        searchCodeField = new JTextField(10);
        inputPanel.add(searchCodeField, gbc);

        panelDelete.add(inputPanel, BorderLayout.NORTH);

        // Create the scrollPane with the employeeTable
        JScrollPane scrollPane = new JScrollPane(customersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelDelete.add(tableContainer, BorderLayout.CENTER);
    }

    public void displayCustomers(List<String[]> customerList) {
        DefaultTableModel model = (DefaultTableModel) customersTable.getModel();
        model.setRowCount(0); // Clear existing data
        for (String[] row : customerList) {
            model.addRow(row); // Add new data
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame customerFrame = new JFrame("Customer Management");
            CustomerManagementPanel panel = new CustomerManagementPanel(customerFrame);

            // Debugging output
            System.out.println("Adding panel to frame");

            customerFrame.setContentPane(panel); // Set the panel as the content pane
            customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            customerFrame.pack(); // Adjust the frame size
            customerFrame.setLocationRelativeTo(null); // Center the frame
            customerFrame.setVisible(true);
        });
    }
}