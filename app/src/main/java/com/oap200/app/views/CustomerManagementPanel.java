// Created by Johnny

package com.oap200.app.views;

import com.oap200.app.models.CustomerDAO;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.prefs.Preferences;

public class CustomerManagementPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    private JTable customerTable;

    public CustomerManagementPanel() {
        initializeFields();

        Preferences prefs = Preferences.userNodeForPackage(CustomerManagementPanel.class);
        int x = prefs.getInt(PREF_X, 50);
        int y = prefs.getInt(PREF_Y, 50);
        setLocation(x, y);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

        setLayout(new BorderLayout());

        JButton backButton = ButtonBuilder.createBlueBackButton(this::closeWindow);
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(this::logoutAction);
        JButton viewButton = ButtonBuilder.createViewButton(this::viewCustomers);
        JButton addButton = ButtonBuilder.createAddButton(this::addCustomer);
        JButton deleteButton = ButtonBuilder.createDeleteButton(this::deleteCustomer);
        JButton updateButton = ButtonBuilder.createUpdateButton(this::updateCustomer);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel1);
        panel1.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Customers", null, panel1, "Click to view");

        // Other tabs can be added similarly...

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        customerTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel1.add(scrollPane, BorderLayout.CENTER);
    }

    private void viewCustomers() {
        CustomerDAO customerDAO = new CustomerDAO();
        List<String[]> customersList = customerDAO.fetchCustomers();
        String[] columnNames = {"Customer Number", "Customer Name", "Contact Last Name", "Contact First Name", "Phone Number",
                "Address Line 1", "Address Line 2", "City", "State", "Postal Code", "Country", "Sales Rep Employee Number", "Credit Limit"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : customersList) {
            model.addRow(row);
        }
        customerTable.setModel(model);
    }

    private void initializeFields() {
        // Initialize any required fields here
    }

    private void addComponentsToPanel(JPanel panel) {
        // Add components to the panel
    }

    private void closeWindow() {
        dispose(); // Closes the window
    }

    private void logoutAction() {
        JOptionPane.showMessageDialog(this, "Logout action triggered");
    }

    private void addCustomer() {
        JOptionPane.showMessageDialog(this, "Add Customer action triggered");
    }

    private void deleteCustomer() {
        JOptionPane.showMessageDialog(this, "Delete Customer action triggered");
    }

    private void updateCustomer() {
        JOptionPane.showMessageDialog(this, "Update Customer action triggered");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerManagementPanel frame = new CustomerManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setVisible(true);
        });
    }
}