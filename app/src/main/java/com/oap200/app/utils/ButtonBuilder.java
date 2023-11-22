// Created by Sindre

package com.oap200.app.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
        JButton backButton = new JButton("BACK");
        backButton.setForeground(Color.WHITE); // Set text color to white
        backButton.setBackground(Color.BLUE); // Set background color to blue
        backButton.setBorderPainted(false); // Remove border
        backButton.setPreferredSize(new Dimension(90, 20)); // Set a specific size
        backButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return backButton;
    }
// Red Logout Button is created by Johnny
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
    // Red Logout Button is created by Sebastian
    public static JButton createActionButton(Runnable action) {
        JButton actionButton = new JButton("View");
        actionButton.setForeground(Color.WHITE); // Set text color to white
        actionButton.setBackground(Color.GREEN); // Set background color to red
        actionButton.setBorderPainted(false); // Remove border
        actionButton.setPreferredSize(new Dimension(100, 20)); // Set a specific size
        actionButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
        return actionButton;
    }

}

