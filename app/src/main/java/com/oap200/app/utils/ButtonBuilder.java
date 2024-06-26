// Created by Sindre

package com.oap200.app.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class ButtonBuilder {
    public static JButton createStyledButton(String text, Runnable action) {
        ImageIcon buttonIcon = new ImageIcon(ButtonBuilder.class.getResource("/imgs/production-icon-19.png"));
        Image resizedImage = buttonIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon buttonIconResized = new ImageIcon(resizedImage);

        JButton button = new JButton(text, buttonIconResized);

        button.setForeground(Color.BLACK); // Set text color
        button.setBackground(new Color(255, 255, 255)); // Set background color
        button.setBorderPainted(true); // Remove border
        button.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left of the icon
        button.setPreferredSize(new Dimension(200, 25)); // Set the desired width and height

        button.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return button;
    }

    // BlueBackButton is created by Johnny
    public static JButton createBlueBackButton(Runnable action) {
        JButton backButton = new JButton("Back");
        backButton.setForeground(Color.WHITE); // Set text color to white
        backButton.setBackground(Color.BLUE); // Set background color to blue
        backButton.setBorderPainted(false); // Remove border
        backButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        backButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return backButton;
    }

    // LogOutButton is created by Johnny
    public static JButton createRedLogoutButton(Runnable action) {
        JButton backButton = new JButton("Logout");
        backButton.setForeground(Color.WHITE); // Set text color to white
        backButton.setBackground(Color.RED); // Set background color to red
        backButton.setBorderPainted(false); // Remove border
        backButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        backButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return backButton;
    }

    // updateButton is created by Sebastian
    public static JButton createUpdateButton(Runnable action) {
        JButton updateButton = new JButton("Update");
        updateButton.setForeground(Color.WHITE); // Set text color to white
        updateButton.setBackground(Color.BLACK); // Set background color to red
        updateButton.setBorderPainted(false); // Remove border
        updateButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        updateButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return updateButton;
    }

    // Universal button is created by Dirkje Jansje van der Poel
public static JButton createButton(String buttonText, Runnable action) {
    JButton button = new JButton(buttonText); // Create button with text
    button.setForeground(Color.WHITE); // Set text color to white
    button.setBackground(Color.BLACK); // Set background color to black
    button.setBorderPainted(false); // Remove border
    button.setPreferredSize(new Dimension(100, 20)); // Set a specific size
    button.addActionListener(e -> {
        if (action != null) {
            action.run();
        }
    });
    return button;
}

     // viewButton is created by Sebastian
    public static JButton createViewButton(Runnable action) {
        JButton viewButton = new JButton("View All");
        viewButton.setForeground(Color.WHITE); // Set text color to white
        viewButton.setBackground(Color.BLACK); // Set background color to red
        viewButton.setBorderPainted(false); // Remove border
        viewButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        viewButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return viewButton;
    }

     //addButton is created by Sebastian
    public static JButton createAddButton(Runnable action) {
        JButton addButton = new JButton("Add");
        addButton.setForeground(Color.WHITE); // Set text color to white
        addButton.setBackground(Color.BLACK); // Set background color to red
        addButton.setBorderPainted(false); // Remove border
        addButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        addButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return addButton;
    }

     // deleteButton is created by Sebastian
    public static JButton createDeleteButton(Runnable action) {
        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.WHITE); // Set text color to white
        deleteButton.setBackground(Color.BLACK); // Set background color to red
        deleteButton.setBorderPainted(false); // Remove border
        deleteButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        deleteButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return deleteButton;
    }

     // Search Button is created by Sebastian
    public static JButton createSearchButton(Runnable action) {
        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE); // Set text color to white
        searchButton.setBackground(Color.BLACK); // Set background color to red
        searchButton.setBorderPainted(false); // Remove border
        searchButton.setPreferredSize(new Dimension(150, 20)); // Set a specific size
        searchButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return searchButton;
    }

//Search button created by Kristian
    public static JButton createSearchNumberButton(Runnable action) {
        JButton searchNumberButton = new JButton("Search");
        searchNumberButton.setForeground(Color.WHITE); // Set text color to white
        searchNumberButton.setBackground(Color.BLACK); // Set background color to red
        searchNumberButton.setBorderPainted(false); // Remove border
        searchNumberButton.setPreferredSize(new Dimension(160, 20)); // Set a specific size
        searchNumberButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return searchNumberButton;
    }

    // RadioButton creation method - Created by Dirkje Jansje van der Poel
    public static JRadioButton createStyledRadioButton(String text, boolean selected) {
        JRadioButton radioButton = new JRadioButton(text, selected);
            return radioButton;
    }
}
