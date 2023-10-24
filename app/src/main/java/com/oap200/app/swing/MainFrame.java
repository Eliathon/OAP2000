/*
 * Created by Sindre
 */

package com.oap200.app.swing;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private JTextArea textArea;
    private Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
    private final String X_POS_KEY = "xPos";
    private final String Y_POS_KEY = "yPos";

    public MainFrame() {
        setTitle("Swing MainFrame");
        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER); // ScrollPane allows the text area to be scrollable

        initPosition(); // Initialize the frame's position

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                storePosition();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initPosition() {
        int lastX = prefs.getInt(X_POS_KEY, -1);
        int lastY = prefs.getInt(Y_POS_KEY, -1);
        if (lastX != -1 && lastY != -1) {
            setLocation(lastX, lastY);
        } else {
            setLocationRelativeTo(null);
        }
    }

    private void storePosition() {
        prefs.putInt(X_POS_KEY, getX());
        prefs.putInt(Y_POS_KEY, getY());
    }

    public void displayEmails(List<String> emails) {
        for (String email : emails) {
            textArea.append(email + "\n");
        }
    }
}
