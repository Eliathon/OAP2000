package com.oap200.app.views;


 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;

class Payment {
    private int customerNumber;
    private String checkNumber;
    private Date paymentDate;
    private double amount;

    public Payment(int customerNumber, String checkNumber, Date paymentDate, double amount) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }
}

public class PaymentManagement {

    public static void main(String[] args) {
        // Get the payment data from the OrderManagementPanel class
        OrderManagementPanel orderPanel = new OrderManagementPanel();
        List<Payment> payments = orderPanel.getPayments();

        // Database connection parameters
        String url = "jdbc:mysql://YOUR_DATABASE_SERVER:PORT/YOUR_DATABASE_NAME";
        String user = "YOUR_DATABASE_USERNAME";
        String password = "YOUR_DATABASE_PASSWORD";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            for (Payment payment : payments) {
                // Insert payment data into the database
                insertPayment(connection, payment);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertPayment(Connection connection, Payment payment) throws SQLException {
        String sql = "INSERT INTO payment_table (customerNumber, checkNumber, paymentDate, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, payment.getCustomerNumber());
            preparedStatement.setString(2, payment.getCheckNumber());

            // Convert Java Date to SQL Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlDate = new java.sql.Date(payment.getPaymentDate().getTime());
            preparedStatement.setDate(3, sqlDate);

            preparedStatement.setDouble(4, payment.getAmount());

            preparedStatement.executeUpdate();
        }
    }
}
