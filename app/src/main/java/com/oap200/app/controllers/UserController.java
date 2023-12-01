package com.oap200.app.controllers;

import java.io.IOException;
import javax.swing.JOptionPane;
import com.oap200.app.utils.ConfigLoader;
import com.oap200.app.utils.PasswordHasher;

public class UserController {

    private String configFilePath = "app/src/main/resources/config.json"; // Changed to forward slashes
    private ConfigLoader configLoader = new ConfigLoader(configFilePath);
    private PasswordHasher passwordHasher = new PasswordHasher();

    public boolean authenticate(String username, char[] password) {
        if (username == null || username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(null, "You must input a valid username and password",
                    "Username or password input fault", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            return passwordHasher.checkPassword(new String(password),
                    configLoader.loadCredentials(username).getPassword());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File reading error", "File reading error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAccount(String username, char[] password) {
        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Username cannot be empty.",
                    "Username Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isPasswordComplex(password)) {
            JOptionPane.showMessageDialog(null,
                    "The password must be at least eight characters long and include at least one digit, one upper case letter, one lower case letter, and one special character.",
                    "Password Complexity Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            return configLoader.createAccount(username, password);
        } catch (Exception e) {
            // Handle exceptions specific to account creation process
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPasswordComplex(char[] password) {
        String passwordStr = new String(password);
        return passwordStr.matches("(?=.*[0-9])" + // At least one digit
                "(?=.*[a-z])" + // At least one lower case letter
                "(?=.*[A-Z])" + // At least one upper case letter
                "(?=.*[!*@#$%^&+=])" + // At least one special character
                "(?=\\S+$).{8,}"); // At least 8 characters
    }
}
