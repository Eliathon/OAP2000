package com.oap200.app.views;

import com.oap200.app.controllers.UserController;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JFrame {

    private UserController userController = new UserController();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;

    public LoginPanel() {
        super("User Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(new CreateAccountPanel(this, userController), "CreateAccount");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JTextField userField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(userField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);

        loginButton.addActionListener(e -> {
            if (userController.authenticate(userField.getText(), passwordField.getPassword())) {
                // Authentication succeeded, close login window
                dispose();
                // Open main application window
                new MainFrame().setVisible(true);
            }
        });

        createAccountButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));

        return loginPanel;
    }

    // Method to switch back to login panel from create account panel
    public void showLoginPanel() {
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPanel().setVisible(true));
    }
}
