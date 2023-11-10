// Created by Sindre

package com.oap200.app.utils;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ConfigLoader {

    private String configFilePath;

    // Constructor that accepts a file path
    public ConfigLoader(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public LoginCredentials loadCredentials(String inputUsername) throws Exception {
        try {
            String data = new String(Files.readAllBytes(Paths.get(configFilePath)));
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                String storedUsername = userObject.getString("username");

                if (storedUsername.equals(inputUsername)) {
                    String storedHashedPassword = userObject.getString("password");
                    return new LoginCredentials(storedUsername, storedHashedPassword);
                }
            }
            throw new Exception("Username not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error reading from the config file.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error processing the credentials.", e);
        }
    }

    // Method to check if the username already exists
    private boolean usernameExists(String username) throws IOException {
        JSONArray usersArray = loadUsers();
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject userObj = usersArray.getJSONObject(i);
            if (userObj.getString("username").equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Method to load the array of users from the config file
    private JSONArray loadUsers() throws IOException {
        File configFile = new File(configFilePath);
        if (!configFile.exists() || configFile.length() == 0) {
            return new JSONArray(); // Empty JSON array if file doesn't exist or is empty
        }
        String content = new String(Files.readAllBytes(Paths.get(configFilePath)));
        return new JSONArray(content);
    }

    private PasswordHasher passwordHasher = new PasswordHasher();

    // Method to create a new account
    public boolean createAccount(String username, char[] password) {
        try {
            String hashedPassword = passwordHasher.hash(new String(password)); // Hash password

            // Check if username already exists
            if (usernameExists(username)) {
                System.out.println("Username already exists.");
                return false;
            }

            // Create user JSON object
            JSONObject userObj = new JSONObject();
            userObj.put("username", username);
            userObj.put("password", hashedPassword); // Store hashed password

            // Add new user to the array and write to the file
            JSONArray usersArray = loadUsers();
            usersArray.put(userObj);

            Files.write(Paths.get(configFilePath), usersArray.toString().getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            return true; // Account created successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Account creation failed
        }
    }

}
