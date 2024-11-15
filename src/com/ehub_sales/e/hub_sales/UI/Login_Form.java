package com.ehub_sales.e.hub_sales.UI;

import java.sql.*;

import javax.swing.*;

import com.ehub_sales.e.hub_sales.Sales.Inventory;
import com.ehub_sales.e.hub_sales.Sales.SalesReport;
import com.ehub_sales.e.hub_sales.Users.Admin;
import com.ehub_sales.e.hub_sales.Users.Customer;


public class Login_Form extends javax.swing.JFrame {
    
    private static Login_Form main = null;
    private final Inventory inventory = new Inventory();
    private final SalesReport salesReport = new SalesReport();
    private final Admin admin = new Admin("A001", "admin", "123", salesReport);
    private final Customer customer = new Customer("C001", "customer", "123");
    
    private Login_Form() {
        initComponents();
    }

    public static Login_Form getInstance() {
        if (main == null) {
            main = new Login_Form();
        }
        return main;
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void resetFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblUsername1 = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnSignUp = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("E-HUB SALES");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(211, 211, 211));
        jPanel1.setPreferredSize(new java.awt.Dimension(260, 400));

        lblWelcome.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("LOGIN");

        lblUsername1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblUsername1.setText("Username");

        lblPassword.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblPassword.setText("Password");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnLogin.setBackground(new java.awt.Color(153, 153, 255));
        btnLogin.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        btnLogin.setText("Log In");
        btnLogin.setToolTipText("");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLogin.setName(""); // NOI18N
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnSignUp.setBackground(new java.awt.Color(153, 153, 255));
        btnSignUp.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsername1)
                    .addComponent(lblPassword)
                    .addComponent(lblWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addGap(64, 64, 64)
                        .addComponent(btnSignUp))
                    .addComponent(txtPassword)
                    .addComponent(txtUsername))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblWelcome)
                .addGap(24, 24, 24)
                .addComponent(lblUsername1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        SignUp_Form signUp = new SignUp_Form();
        signUp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser = "root";
        String dbPassword = "";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both username and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM users WHERE Username = ?";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String passDb = rs.getString("Password");
                String fname = rs.getString("Username");

                if (password.equals(passDb)) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + fname + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    if ("Admin".equals(rs.getString("Role"))) {
                        new Admin_Dashboard(this, new Admin(rs.getString("UserID"), fname, passDb, new SalesReport()), new Inventory(), new SalesReport()).setVisible(true);
                    } else {
                        new Customer_Dashboard(this, new Customer(rs.getString("UserID"), fname, passDb), new Inventory()).setVisible(true);
                    }
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username not found. Please check and try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Reset fields after each attempt
        txtUsername.setText("");
        txtPassword.setText("");
    }//GEN-LAST:event_btnLoginActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Login_Form login = getInstance();
            login.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername1;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
