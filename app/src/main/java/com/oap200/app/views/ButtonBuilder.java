// Created by Sindre

package com.oap200.app.views;

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
}
