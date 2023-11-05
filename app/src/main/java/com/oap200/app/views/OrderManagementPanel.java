package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OrderManagementPanel extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private Connection connection;
    private JButton viewButton;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField  orderNumberField, orderDateField, requiredDateField, shippedDateField, statusField, commentsField, customerNumberField;
    private JTextField  productCodeField, quantityOrderedField, priceEachField, OrderLineNumberField; 
  
   
   public class OrderManagementPanel extends JFrame {

    public static void main(String[]args) {
        JFrame frame = new JFrame("Order Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton viewButton = new JButton("View");
        JButton createButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.NORTH);

        JTable orderTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(orderTable);

        frame.add(scrollPane, BorderLayout.Center);

        frame.setVisible(true);

        }
   
        public List<Order> fetchOrdersFromDatabase(Connection connection) {
            List<Order> orders = new ArrayList<>();

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");

                while (resultSet.next()) {
                    int orderNumber = resultSet.getInt("order_number");
                    int customerNumber = resultSet.getInt("costumer_number");
                    int productCode = resultSet.getInt("product_code");
                    int quantityOrdered = resultSet.getInt("quantity_ordered");
                    String status = resultSet.getInt("status");
                    String productName = resultSet.getInt("prodcut_name");

                    Order order = new Order(orderNumber, customerName, productCode, quantityOrdered, status);
                    orders.add(order);
                }

                resultSet.close();
                statement.close();
            } 
                catch (SQLException e) {

                  e.printStackTrace();  
                }
                
                return orders;
             
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
                List<Order> orders = fetchOrdersFromDatabase(connection);
           
            DefaultTableModel model = new DefaultTableModel();

        DefaultTableModel.addColumn("Ordernumber");
        DefaultTableModel.addColumn("Costumernumber");
        DefaultTableModel.addColumn("ProductCode");
        DefaultTableModel.addColumn("Status");
        DefaultTableModel.addColumn("Quantity ordered");

        for(Order order : orders) {
            classicmodels.addRow(new Object[]{order.getOrderNumber(), order.getCustomerNumber(), order.getQuantityOrdered(), order.getStatus()});
        }
           orderTable.setModel(DefaultTableModel);
    }
       });

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
            JDialong createDialog = new JDialog();
            createDialog.setLayout(new GridLayout(0,2));

            JLabel customerNumberLabel = new JLabel("Costumer Number:");
            JTextField costumerNumberField = new JTextField();

            JLabel productNameLabel = new JLabel("Product Name:");
            JTextField productNameField = new JTextField();

            JLabel productCodeLabel = new JLabel("Product Code");
            JTextField productCodeField = new JLabel();

            JLabel quantityOrderedLabel = new JLabel("QuantityOrdered");
            JTextField quantityOrderedField = new JLabel();

            JLabel statusLabel = new JLabel("Status");
            JTextField statusField = new JLabel();
            
                
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
                int selectedRow = orderTable.getSelectedRow();

                if (selectedRow >= 0) {

                    int orderNumber = (int) orderTable.getValueAt(selectedRow, 0);
                    String customerName = (String) orderTable.getValueAt(selectedRow, 1);
                    String productName = (String) orderTable.getValueAt(selectedRow, 2);
                    int productCode = (String) orderTable
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the Delete functionality to remove an order
            }
        });

        // Set up the database connection
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
            // Use 'connection' to execute database queries
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrderManagementPanel().setVisible(true);
            }
        });
    }
   }

