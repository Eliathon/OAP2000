package com.oap200.app.controllers;


import com.oap200.app.models.PaymentsDAO;
    import com.oap200.app.views.PaymentManagement;
    
    import java.math.BigDecimal;
    import java.util.List;
    
    import javax.swing.JOptionPane;
public class PaymentsController {
    
   
    
        // Metode for å håndtere visning av produkter
            private PaymentsDAO paymentsDAO;
            private PaymentManagement paymentManagement;
        
            public PaymentsController(PaymentsDAO paymentsDAO, PaymentManagement paymentManagement) {
                this.paymentsDAO = paymentsDAO;
                this.paymentManagement = paymentManagement;
                
            }
            public void handleViewAllPayments() {
                List<String[]> allPayments = paymentsDAO.fetchPayments();
                paymentManagement.displayProducts(allPayments);
            }
    
            public boolean handleDeletePayments(String checkNumber) {
                boolean deletionSuccessful = paymentsDAO.deletePayments(checkNumber);
                if (deletionSuccessful) {
                    return true;
                } else {
                    // Håndter feil her, for eksempel vis en feilmelding
                    return false;
                }
            }
    
            private void updatePayments() {
                List<String[]> allPayments = paymentsDAO.fetchPayments();
                paymentManagement.displaypayments(allPayments);
            }
            
            public boolean handleAddPayments(String customerNumber, String checkNumber, String paymentDate, String amount) {
                // Logikken for å legge til et produkt i databasen
                // Du kan kalle productsDAO eller andre relevante metoder her
        
                // Returner true hvis tillegg var vellykket, ellers false
                return true; // eller false basert på faktisk tilstand
            }
            
            public PaymentsDAO getPaymentsDAO() {
                return this.paymentsDAO;
            }
            
    
    
            
    
        
    } 


}
