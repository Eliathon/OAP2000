package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder;

public class EmployeeManagementPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    public EmployeeManagementPanel() {
        initializeFields();

        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(EmployeeManagementPanel.class);
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
        addComponentsToPanelView(panel1);
        panel1.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Employee", null, panel1, "Click to view");

        // Tab 2: Add Employee
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Employee", null, panel2, "Click to add");

        // Tab 3: Update Products
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Products", null, panel3, "Click to Update");

        // Tab 4: Delete Products
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel4);
        panel4.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Products", null, panel4, "Click to Delete");

        // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    private void initializeFields() {
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
    }

    private void addComponentsToPanel(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(8, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1)); // 8 fields

        // Cloning fields for each tab
        JTextField employeeId = new JTextField(10);
        JTextField lastName = new JTextField(10);
        JTextField firstName = new JTextField(10);
        JTextField extension = new JTextField(10);
        JTextField email = new JTextField(10);
        JTextField officeCode = new JTextField(10);
        JTextField reportsTo = new JTextField(10);
        JTextField jobTitle = new JTextField(10);

        labelPanel.add(new JLabel("Employee ID:"));
        fieldPanel.add(employeeId);
        labelPanel.add(new JLabel("Last Name:"));
        fieldPanel.add(lastName);
        labelPanel.add(new JLabel("First Name:"));
        fieldPanel.add(firstName);
        labelPanel.add(new JLabel("Extension:"));
        fieldPanel.add(extension);
        labelPanel.add(new JLabel("Email:"));
        fieldPanel.add(email);
        labelPanel.add(new JLabel("Office Code:"));
        fieldPanel.add(officeCode);
        labelPanel.add(new JLabel("Reports To:"));
        fieldPanel.add(reportsTo);
        labelPanel.add(new JLabel("Job Title:"));
        fieldPanel.add(jobTitle);

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        JPanel labelPanel = new JPanel(new GridLayout(2, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(2, 1)); // 8 fields

        JTextField employeeId = new JTextField(10);
        JTextField lastName = new JTextField(10);

        labelPanel.add(new JLabel("Employee ID:"));
        fieldPanel.add(employeeId);
        labelPanel.add(new JLabel("Last Name:"));
        fieldPanel.add(lastName);

        panelView.add(labelPanel, BorderLayout.WEST);
        panelView.add(fieldPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementPanel frame = new EmployeeManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Employee Management");
            frame.pack();
            frame.setVisible(true);
        });
    }
}
