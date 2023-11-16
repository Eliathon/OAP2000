package com.oap200.app.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class TabbedPaneExample extends JFrame {

    public TabbedPaneExample() {
        // Create the JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the first tab.
        JPanel panel1 = new JPanel();
        // Add components to panel1...
        tabbedPane.addTab("View ", null, panel1, "Click to view");

        // Create the second tab.
        JPanel panel2 = new JPanel();
        // Add components to panel2...
        tabbedPane.addTab("Add ", null, panel2, "Click to add ");

         // Create the third tab.
        JPanel panel3 = new JPanel();
        // Add components to panel1...
        tabbedPane.addTab("Update ", null, panel3, "Click to update ");     
        
        // Create the forth.
        JPanel panel4= new JPanel();
        // Add components to panel1...
        tabbedPane.addTab("Delete ", null, panel4, "Click to delete ");        
 
 
 
        // Similarly, you can add more tabs here.

        // Add the tabbedPane to this frame
        getContentPane().add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TabbedPaneExample frame = new TabbedPaneExample();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300); // Set the frame size
                frame.setVisible(true); // Display the frame
            }
        });
    }
}