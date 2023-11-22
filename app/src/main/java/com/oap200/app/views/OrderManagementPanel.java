//  Created by Patrik //

// View, Add, Update and Delete Orders

package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class OrderManagementPanel {

    private JFrame frame;
    private JTable resultTable;
    private JButton viewButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField orderNumberField;
    private JTextField orderDateField;
    private JTextField requiredDateField;
    private JTextField shippedDateField;
    private JTextField statusField;
    private JTextArea commentsDescriptionArea;
    private JTextField customerNumberField;
    private JTextArea resultMessageArea;

    private Connection connection;

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            viewPanel();

                createAndShowGUI();
            }
        });
    }
   
    private Connection getConnection() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/classicmodels";
    String username = "root";
    String password = "";
    
    try {
        
        Class.forName("com.mysql.cj.jdbc.Driver");   
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
    } catch (ClassNotFoundException |SQLException e) {
        e.printStackTrace();
    }
    return DriverManager.getConnection(url, username, password);
}  

	private JPanel viewPanel() {
		JPanel viewPanel = new JPanel(new BorderLayout());
		viewButton = new JButton("View Order");
		resultTable = new JTable();
		JScrollPane tableScrollPane = new JScrollPane(resultTable);
		tableScrollPane.setPreferredSize(new Dimension(500, 300));

		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewOrder();
			}
		});

		JPanel inputPanel = new JPanel();
		inputPanel.add(new JLabel("Enter Order Number:"));
		orderNumberField = new JTextField(10);
		inputPanel.add(orderNumberField);
		inputPanel.add(viewButton);

		viewPanel.add(inputPanel, BorderLayout.NORTH);
		viewPanel.add(tableScrollPane, BorderLayout.CENTER);

		return viewPanel;
	}


    private JPanel addPanel() {
        JPanel addPanel = new JPanel();
        addButton = new JButton("Add Order");
         addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        addPanel.add(addButton);

        orderNumberField = new JTextField(10);
        orderDateField = new JTextField(10);
        requiredDateField = new JTextField();
        shippedDateField = new JTextField();
        statusField = new JTextField();
        commentsDescriptionArea = new JTextArea();
        customerNumberField = new JTextField();

        addPanel.add(new JLabel("Order Number"));
        addPanel.add(orderNumberField);

        addPanel.add(new JLabel("Order Date"));
        addPanel.add(orderDateField);

        addPanel.add(new JLabel("Required Date"));
        addPanel.add(requiredDateField);

        addPanel.add(new JLabel("Shipped Date"));
        addPanel.add(shippedDateField);

        addPanel.add(new JLabel("Status"));
        addPanel.add(statusField);

        addPanel.add(new JLabel("Order Comments"));
        addPanel.add(commentsDescriptionArea);

        addPanel.add(new JLabel("Customer Number"));
        addPanel.add(customerNumberField);


        return addPanel;
    }

    private JPanel updatePanel() {
        JPanel updatePanel = new JPanel();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });

        updatePanel.add(updateButton);

        orderNumberField = new JTextField(10);
        orderDateField = new JTextField(10);
        requiredDateField = new JTextField();
        shippedDateField = new JTextField();
        statusField = new JTextField();
        commentsDescriptionArea = new JTextArea();
        customerNumberField = new JTextField();

        updatePanel.add(new JLabel("Order Number"));
        updatePanel.add(orderNumberField);

        updatePanel.add(new JLabel("Order Date"));
        updatePanel.add(orderDateField);

        updatePanel.add(new JLabel("Required Date"));
        updatePanel.add(requiredDateField);

        updatePanel.add(new JLabel("Shipped Date"));
        updatePanel.add(shippedDateField);

        updatePanel.add(new JLabel("Status"));
        updatePanel.add(statusField);

        updatePanel.add(new JLabel("Order Comments"));
        updatePanel.add(commentsDescriptionArea);

        updatePanel.add(new JLabel("Customer Number"));
        updatePanel.add(customerNumberField);
        
   

        return updatePanel;
    }

    private JPanel deletePanel() {
        JPanel deletePanel = new JPanel();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        deletePanel.add(deleteButton);

        orderNumberField = new JTextField(10);
        orderDateField = new JTextField(10);
        requiredDateField = new JTextField();
        shippedDateField = new JTextField();
        statusField = new JTextField();
        commentsDescriptionArea = new JTextArea();
        customerNumberField = new JTextField();

        deletePanel.add(new JLabel("Order Number"));
        orderNumberField = new JTextField(10);
        deletePanel.add(orderNumberField);


        return deletePanel;
    }
    
    private void createAndShowGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int windowWidth = (int) (width * 1); 
        int windowHeight = (int) (height * 1);
        frame = new JFrame("Order Management");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tabbedPane = new JTabbedPane();
    
        // View Orders Tab
        JPanel viewPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("View Orders", viewPanel);
       
        JButton viewButton = new JButton("View All Orders");
        JTextArea searchTextArea = new JTextArea();
        searchTextArea.setColumns(100);
        searchTextArea.setRows(50);
        
    
        viewPanel.add(viewButton, BorderLayout.NORTH);
        viewPanel.add(searchTextArea);
       

        viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewOrder();
			}
		});
    
    
        // Add Order Tab
        JPanel addPanel = new JPanel();
        tabbedPane.addTab("Add Order", addPanel);
        
        JLabel orderNumberAddLabel = new JLabel("Order Number");
        JTextField orderNumberAddField = new JTextField(7);
        addPanel.add(orderNumberAddLabel);
        addPanel.add(orderNumberAddField);
       
        JLabel orderDateAddLabel = new JLabel("Order Date");;
        JTextField orderDateAddField = new JTextField(10);
        addPanel.add(orderDateAddLabel);
        addPanel.add(orderDateAddField);
        
        JLabel requiredDateAddLabel = new JLabel("Required Date");;
        JTextField requiredDateAddField = new JTextField(10);
        addPanel.add(requiredDateAddLabel);
        addPanel.add(requiredDateAddField);
        

        JLabel shippedDateAddLabel = new JLabel("Shipped Date");;
        JTextField shippedDateAddField = new JTextField(10);
        addPanel.add(shippedDateAddLabel);
        addPanel.add(shippedDateAddField);

        JLabel statusAddLabel = new JLabel("Order Status");;
        JTextField statusAddField = new JTextField(10);
        addPanel.add(statusAddLabel);
        addPanel.add(statusAddField);

        JLabel commentsAddLabel = new JLabel("Order Comments");
        JTextField commentsAddField = new JTextField(10);
        addPanel.add(commentsAddLabel);
        addPanel.add(commentsAddField);
        
        JLabel customerNumberAddLabel = new JLabel("Customer Number");
        JTextField customerNumberAddField = new JTextField(10);
        addPanel.add(customerNumberAddLabel);
        addPanel.add(customerNumberAddField);
        
        JButton addButton = new JButton("Add Order");
        addPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        addPanel.add(addButton);

     // Update Order Tab
        JPanel updatePanel = new JPanel();
        updatePanel.add(new JLabel("Update"));
        tabbedPane.addTab("Update Order", updatePanel);
        
        JLabel orderNumberUpdateLabel = new JLabel("Order Number");
        JTextField orderNumberUpdateField = new JTextField(7);
        updatePanel.add(orderNumberUpdateLabel);
        updatePanel.add(orderNumberUpdateField);
       
        JLabel orderDateUpdateLabel = new JLabel("Order Date");;
        JTextField orderDateUpdateField = new JTextField(10);
        updatePanel.add(orderDateUpdateLabel);
        updatePanel.add(orderDateUpdateField);
        
        JLabel requiredDateUpdateLabel = new JLabel("Required Date");;
        JTextField requiredDateUpdateField = new JTextField(10);
        updatePanel.add(requiredDateUpdateLabel);
        updatePanel.add(requiredDateUpdateField);
        

        JLabel shippedDateUpdateLabel = new JLabel("Shipped Date");;
        JTextField shippedDateUpdateField = new JTextField(10);
        updatePanel.add(shippedDateUpdateLabel);
        updatePanel.add(shippedDateUpdateField);

        JLabel statusUpdateLabel = new JLabel("Order Status");;
        JTextField statusUpdateField = new JTextField(10);
        updatePanel.add(statusUpdateLabel);
        updatePanel.add(statusUpdateField);

        JLabel commentsUpdateLabel = new JLabel("Order Comments");
        JTextField commentsUpdateField = new JTextField(10);
        updatePanel.add(commentsUpdateLabel);
        updatePanel.add(commentsUpdateField);
        
        JLabel customerNumberUpdateLabel = new JLabel("Customer Number");
        JTextField customerNumberUpdateField = new JTextField(10);
        updatePanel.add(customerNumberUpdateLabel);
        updatePanel.add(customerNumberUpdateField);
       
        JButton updateButton = new JButton("Update Order");
        updatePanel.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        
        // Delete Order Tab
        JPanel deletePanel = new JPanel();
        deletePanel.add(new JLabel("Delete"));
        tabbedPane.addTab("Delete Order", deletePanel);

        JLabel orderNumberDeleteLabel = new JLabel("Order Number");
        JTextField orderNumberDeleteField = new JTextField(7);
        deletePanel.add(orderNumberDeleteLabel);
        deletePanel.add(orderNumberDeleteField);
       
        JLabel customerNumberDeleteLabel = new JLabel("Customer Number");
        JTextField customerNumberDeleteField = new JTextField(10);
        deletePanel.add(customerNumberDeleteLabel);
        deletePanel.add(customerNumberDeleteField);

        JButton deleteButton = new JButton("Delete Order");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        deletePanel.add(deleteButton);
       
        deletePanel.add(deleteButton);
    
        frame.add(tabbedPane);
        frame.setSize(1100, 600);
        frame.setVisible(true);
    }


private void viewOrder() {
    String query = "SELECT * FROM orders";

    try (Connection connection = getConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            String orderNumber = resultSet.getString("orderNumber");
            String orderDate = resultSet.getString("orderDate");
            String requiredDate = resultSet.getString("requiredDate");
            String shippedDate = resultSet.getString("shippedDate");
            String status = resultSet.getString("status");
            String comments = resultSet.getString("comments");
            String customerNumber = resultSet.getString("customerNumber");
            System.out.println(orderNumber + "%" + orderDate + "%" + requiredDate + "%" + shippedDate + "%" + status + "%" + comments + "%" + customerNumber);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void addOrder() {
    String orderNumber = orderNumberField.getText();
    String orderDate = orderDateField.getText();
    String requiredDate = requiredDateField.getText();
    String shippedDate = shippedDateField.getText();
    String status = statusField.getText();
    String comments = commentsField.getText();
    String customerNumber = customerNumberField.getText();

    String query = "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber) VALUES (?,?,?,?,?,?,?)";

    try (Connection connection = getConnection(); 
    PreparedStatement statement = connection.prepareStatement(query)) {
    
    statement.setString(1, orderNumber);
    statement.setString(2, orderDate);
    statement.setString(3, requiredDate);
    statement.setString(4, shippedDate);
    statement.setString(5, status);
    statement.setString(6, comments);
    statement.setString(7, customerNumber);

    int rowsInserted = statement.executeUpdate();
    if (rowsInserted > 0) {
        System.out.println("A new order has been added!");
    }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

 private void updateOrder() {
    String orderNumber = orderNumberField.getText();
    String orderDate = orderDateField.getText();
    String requiredDate = requiredDateField.getText();
    String shippedDate = shippedDateField.getText();
    String status = statusField.getText();
    String comments = commentsField.getText();
    String customerNumber = customerNumberField.getText();

    String query = "UPDATE orders SET orderDate =?, requiredDate =?, shippedDate =?, status =?, comments =?, customerNumber =? WHERE orderNumber =?";

    try (Connection connection = getConnection();
    PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, orderDate);
        statement.setString(2, requiredDate);
        statement.setString(3, shippedDate);
        statement.setString(4, status);
        statement.setString(5, comments);
        statement.setString(6, customerNumber);
        statement.setString(7, orderNumber);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Order updated successfully!");
        } else {
            System.out.println("No order found with the provided order number.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void deleteOrder() {
        String orderNumber = orderNumberField.getText();
        String customerNumber = customerNumberField.getText();

        String query = "DELETE FROM orders WHERE orderNumber =? AND customerNumber =?";
        
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderNumber);
            statement.setString(2, customerNumber);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Order deleted successfully!");
        } else {
            System.out.println("Error deleting order.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public static void main(String[] args) {
        new OrderManagementPanel().start();
    }
}