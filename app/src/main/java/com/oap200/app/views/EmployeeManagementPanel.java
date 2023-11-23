package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder; // Ensure this class exists and functions as expected

public class EmployeeManagementPanel extends JFrame {
    private JTextField employeeIdField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField extensionField;
    private JTextField emailField;
    private JTextField officeCodeField;
    private JTextField reportsToField;
    private JTextField jobTitleField;

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    public EmployeeManagementPanel() {
        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(EmployeeManagementPanel.class); // Ensure this refers to the
                                                                                           // current class
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

        // Set up the layout for the frame
        setLayout(new BorderLayout());

        // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        // Initialize ButtonBuilder buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            /* Action for Back Button */});
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            /* Action for Logout Button */});
        JButton viewButton = ButtonBuilder.createViewButton(() -> {
            /* Action for View Button */});
        JButton addButton = ButtonBuilder.createAddButton(() -> {
            /* Action for Add Button */});
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> {
            /* Action for Delete Button */});
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> {
            /* Action for Update Button */});

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Employee
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(viewButton, BorderLayout.CENTER);
        tabbedPane.addTab("View Employee", null, panel1, "Click to view");

        // Tab 2: Add Employee
        JPanel panel2 = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(8, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1)); // 8 fields

        // Initialize and add fields and labels
        employeeIdField = new JTextField(10);
        lastNameField = new JTextField(10);
        firstNameField = new JTextField(10);
        extensionField = new JTextField(10);
        emailField = new JTextField(10);
        officeCodeField = new JTextField(10);
        reportsToField = new JTextField(10);
        jobTitleField = new JTextField(10);

        labelPanel.add(new JLabel("Employee ID:"));
        fieldPanel.add(employeeIdField);
        labelPanel.add(new JLabel("Last Name:"));
        fieldPanel.add(lastNameField);
        labelPanel.add(new JLabel("First Name:"));
        fieldPanel.add(firstNameField);
        labelPanel.add(new JLabel("Extension:"));
        fieldPanel.add(extensionField);
        labelPanel.add(new JLabel("Email:"));
        fieldPanel.add(emailField);
        labelPanel.add(new JLabel("Office Code:"));
        fieldPanel.add(officeCodeField);
        labelPanel.add(new JLabel("Reports To:"));
        fieldPanel.add(reportsToField);
        labelPanel.add(new JLabel("Job Title:"));
        fieldPanel.add(jobTitleField);

        panel2.add(labelPanel, BorderLayout.WEST);
        panel2.add(fieldPanel, BorderLayout.CENTER);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Employee", null, panel2, "Click to add");

        // Tab 3: Update Products
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.add(updateButton, BorderLayout.CENTER);
        tabbedPane.addTab("Update Products", null, panel3, "Click to Update");

        // Tab 4: Delete Products
        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.add(deleteButton, BorderLayout.CENTER);
        tabbedPane.addTab("Delete Products", null, panel4, "Click to Delete");

        // Adding components to buttonPanel and topPanel
        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementPanel frame = new EmployeeManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Employee Management");
            frame.pack(); // Adjust to fit components
            frame.setVisible(true);
        });
    }
}
