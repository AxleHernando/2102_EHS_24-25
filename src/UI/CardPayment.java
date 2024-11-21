package UI;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class CardPayment extends javax.swing.JFrame {

    private double totalAmount;

    public CardPayment(double total) {
        initComponents();
        this.totalAmount = total;
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en", "PH")).format(total)); // Set the total
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnProceed = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();
        lblWelcome1 = new javax.swing.JLabel();
        txtCardNo = new javax.swing.JTextField();
        lblWelcome2 = new javax.swing.JLabel();
        txtCVC = new javax.swing.JTextField();
        lblWelcome3 = new javax.swing.JLabel();
        txtCardHolder = new javax.swing.JTextField();
        lblWelcome4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        lblWelcome5 = new javax.swing.JLabel();
        txtExpiry = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnProceed.setBackground(new java.awt.Color(153, 153, 255));
        btnProceed.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnProceed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Payment.png"))); // NOI18N
        btnProceed.setText("Proceed to Payment");
        btnProceed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProceed.setIconTextGap(10);
        btnProceed.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnProceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProceedActionPerformed(evt);
            }
        });

        lblWelcome.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Shopaloo");

        lblWelcome1.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblWelcome1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblWelcome1.setText("CARD NUMBER");

        txtCardNo.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        lblWelcome2.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblWelcome2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblWelcome2.setText("CVC");

        txtCVC.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        lblWelcome3.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblWelcome3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblWelcome3.setText("CARD HOLDER NAME");

        txtCardHolder.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        lblWelcome4.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblWelcome4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblWelcome4.setText("EXPIRY");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTotal.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblWelcome5.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblWelcome5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblWelcome5.setText("TOTAL");

        txtExpiry.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addGap(35, 35, 35))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblWelcome3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWelcome1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCVC, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWelcome2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblWelcome4)
                            .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblWelcome5))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtCardHolder)
                    .addComponent(btnProceed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblWelcome)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWelcome1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWelcome2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCVC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblWelcome3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCardHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWelcome4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWelcome5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtExpiry))
                .addGap(18, 18, 18)
                .addComponent(btnProceed, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
       
    public String getCardNumber() {
        return txtCardNo.getText().trim();
    }

    public String getCardHolder() {
        return txtCardHolder.getText().trim();
    }

    public String getExpiryDate() {
        return txtExpiry.getText().trim();
    }

    public String getCVC() {
        return txtCVC.getText().trim();
    }

    // Method to validate the logged-in user
    private String getLoggedInUserID() {
        String loggedInUserID = Login_Form.loggedInUserID; // Replace this with your session logic
        if (loggedInUserID == null || loggedInUserID.isEmpty()) {
            System.out.println("No logged-in user ID found.");
        }
        return loggedInUserID;
    }

    // Validate customer ID in the database
    private boolean validateCustomerID(String customerId) {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser = "root";
        String dbPassword = "";

        String checkCustomerQuery = "SELECT COUNT(*) FROM users WHERE UserID = ?";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = con.prepareStatement(checkCustomerQuery)) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error validating CustomerID: " + e.getMessage());
        }
        return false;
    }

    // Validate card input fields
    private boolean validateCardInput() {
        String cardNumber = getCardNumber();
        String cardHolder = getCardHolder();
        String expiryDate = getExpiryDate();
        String cvc = getCVC();

        if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiryDate.isEmpty() || cvc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!cardNumber.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, "Card number must be 16 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!cvc.matches("\\d{3}")) {
            JOptionPane.showMessageDialog(this, "CVC must be 3 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!expiryDate.matches("\\d{1,2}/\\d{1,2}")) {
            JOptionPane.showMessageDialog(this, "Expiry date must be in MM/YY format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Insert card details into the database
    private void insertCardDetailsToDatabase(String customerId, String cardNumber, String cvc, String cardHolder, String expiryDate, double paymentAmount) {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser = "root";
        String dbPassword = "";

        String insertCardQuery = "INSERT INTO CardPayment (UserID, CardNo, CVC, CardHolder, EXPIRY, Payment) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword); PreparedStatement ps = con.prepareStatement(insertCardQuery)) {
            ps.setString(1, customerId);
            ps.setString(2, cardNumber);
            ps.setString(3, cvc);
            ps.setString(4, cardHolder);
            ps.setString(5, expiryDate);
            ps.setDouble(6, paymentAmount);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println("Error inserting card details: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred while processing payment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnProceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProceedActionPerformed
        if (!validateCardInput()) {
            return;
        }

        String customerId = getLoggedInUserID();
        if (!validateCustomerID(customerId)) {
            JOptionPane.showMessageDialog(this, "Invalid CustomerID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        insertCardDetailsToDatabase(
                customerId,
                getCardNumber(),
                getCVC(),
                getCardHolder(),
                getExpiryDate(),
                totalAmount
        );
        
        this.dispose();
    }//GEN-LAST:event_btnProceedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CardPayment(0.00).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProceed;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel lblWelcome1;
    private javax.swing.JLabel lblWelcome2;
    private javax.swing.JLabel lblWelcome3;
    private javax.swing.JLabel lblWelcome4;
    private javax.swing.JLabel lblWelcome5;
    private javax.swing.JTextField txtCVC;
    private javax.swing.JTextField txtCardHolder;
    private javax.swing.JTextField txtCardNo;
    private javax.swing.JTextField txtExpiry;
    // End of variables declaration//GEN-END:variables
}
