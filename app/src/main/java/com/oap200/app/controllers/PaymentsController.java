package com.oap200.app.controllers;


import com.oap200.app.models.PaymentsDAO;
    import com.oap200.app.views.PaymentManagement;
    
    import java.math.BigDecimal;
    import java.util.List;
    
    import javax.swing.JOptionPane;
public class PaymentsController {
    
   
    
        // Method to handle showing different classes
            private PaymentsDAO paymentsDAO;
            private PaymentManagement paymentManagement;
        
            public PaymentsController(PaymentsDAO paymentsDAO, PaymentManagement paymentManagement) {
                this.paymentsDAO = paymentsDAO;
                this.paymentManagement = paymentManagement;
                
            }
          
    
            public boolean handleDeletePayments(String checkNumber) {
                boolean deletionSuccessful = paymentsDAO.deletePayments(checkNumber);
                if (deletionSuccessful) {
                    return true;
                } else {
                    // Handles errors, like failure to perform.
                    return false;
                }
            }
    
            private void updatePayments() {
                List<String[]> allPayments = paymentsDAO.fetchPayments();
                paymentManagement.displaypayments(allPayments);
            }
            
            public boolean handleAddPayments(String customerNumber, String checkNumber, String paymentDate, String amount) {
                // Logic to add a payment to the database.
                // Able to call paymentdao and other methods here.
        
                // Returns to tell if result is true or false.
                return true; // or false based on actual state.
            }
            
            public PaymentsDAO getPaymentsDAO() {
                return this.paymentsDAO;
            }
            
    
    
            
    
        
    } 


