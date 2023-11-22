// Created by Johnny
package com.oap200.app.tabbedPanels;

import javax.swing.*;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder;

public class TabbedEmployeePanel extends JPanel {

    public TabbedEmployeePanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab for Viewing Employees
        JPanel viewEmployeesPanel = new JPanel();
        // TODO: Add components to viewEmployeesPanel...
        tabbedPane.addTab("View Employees", null, viewEmployeesPanel, "Click to view employees");

        // Tab for Adding Employees
        JPanel addEmployeesPanel = new JPanel();
        // TODO: Add components to addEmployeesPanel...
        tabbedPane.addTab("Add Employee", null, addEmployeesPanel, "Click to add employee");

        // Tab for Updating Employees
        JPanel updateEmployeesPanel = new JPanel();
        // TODO: Add components to updateEmployeesPanel...
        tabbedPane.addTab("Update Employee", null, updateEmployeesPanel, "Click to update employee");

        // Tab for Deleting Employees
        JPanel deleteEmployeesPanel = new JPanel();
        // TODO: Add components to deleteEmployeesPanel...
        tabbedPane.addTab("Delete Employee", null, deleteEmployeesPanel, "Click to delete employee");

        add(tabbedPane, BorderLayout.CENTER);

        // Top panel with 'Back' and 'Logout' buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            System.out.println("Back button clicked");
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            System.out.println("Logout button clicked");
        });

        topPanel.add(backButton);
        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);
    }
}