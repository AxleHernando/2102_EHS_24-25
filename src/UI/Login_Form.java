package UI;

import UI.Admin.Admin_Dashboard;
import UI.Customer.Customer_Dashboard;
import Databases.DBConnection;
import Objects.User;
import Objects.UserSession;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.sql.*;


public class Login_Form extends javax.swing.JFrame {
    private static Login_Form main = null;
    public static String loggedInUserID;

    private Login_Form() {
        initComponents();
        setIcon();
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
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
        tbShowPass = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log in");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(211, 211, 211));
        jPanel1.setPreferredSize(new java.awt.Dimension(275, 550));

        lblWelcome.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADMIN\\Downloads\\SHOP-A-LOO (1) (2).png")); // NOI18N

        lblUsername1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblUsername1.setText("Username");

        lblPassword.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblPassword.setText("Password");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(153, 153, 255));
        btnLogin.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/log-in.png"))); // NOI18N
        btnLogin.setText("Log In");
        btnLogin.setToolTipText("");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnLogin.setMaximumSize(new java.awt.Dimension(88, 31));
        btnLogin.setMinimumSize(new java.awt.Dimension(88, 31));
        btnLogin.setName(""); // NOI18N
        btnLogin.setPreferredSize(new java.awt.Dimension(88, 31));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        btnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoginKeyPressed(evt);
            }
        });

        btnSignUp.setBackground(new java.awt.Color(153, 153, 255));
        btnSignUp.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignUp.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnSignUp.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        tbShowPass.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        tbShowPass.setText("Show");
        tbShowPass.setBorder(null);
        tbShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbShowPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(lblWelcome)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tbShowPass, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPassword)
                            .addComponent(txtUsername)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPassword)
                                    .addComponent(lblUsername1))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsername1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbShowPass, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 320));

        jPanel2.setBackground(new java.awt.Color(239, 239, 239));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 280, -1));

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

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both username and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM users WHERE Username = ?";
        String queryLogs = "INSERT INTO user_logs (UserID, FullName, Role, Action, Date, Time, Notes) VALUES (?, ?, ?, ?, "
        + "STR_TO_DATE(DATE_FORMAT(CURDATE(), '%m/%d/%Y'), '%m/%d/%Y'), "
        + "DATE_FORMAT(NOW(), '%H:%i:%s'), ?)";
        try(Connection con = DBConnection.Connect();
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement psLogs = con.prepareStatement(queryLogs)) {
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                String role = rs.getString("Role");
                loggedInUserID = rs.getString("UserID");
                String fullName = rs.getString("FullName");
                
                User loggedInUser = new User(loggedInUserID, username, storedPassword);
                UserSession.setCurrentUser(loggedInUser);
                String userID = UserSession.getCurrentUserID();
                
                if (storedPassword.equals(password)) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + fullName + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    psLogs.setString(1, userID);
                    psLogs.setString(2, fullName);
                    psLogs.setString(3, role);
                    psLogs.setString(4, "Logged In");
                    psLogs.setString(5, fullName + " Logged In as " + role + ".");
                    psLogs.executeUpdate();
                    
                    switch (role) {
                        case "Customer":
                            Customer_Dashboard customerDashboard = new Customer_Dashboard();
                            customerDashboard.setVisible(true);
                            resetFields();
                            this.dispose();
                            break;
                        case "Admin":
                            Admin_Dashboard supplierDashboard = new Admin_Dashboard();
                            supplierDashboard.setVisible(true);
                            resetFields();
                            this.dispose();
                            break;
                        default:
                            showErrorMessage("Invalid role.");
                    }
                } else {
                    showErrorMessage("Incorrect password.");
                }
            } else {
                showErrorMessage("User  not found.");
            }
        } catch (SQLException e) {
            showErrorMessage("Database error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
            btnLoginActionPerformed(null);
        }
    }//GEN-LAST:event_btnLoginKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
            btnLoginActionPerformed(null);
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void tbShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbShowPassActionPerformed
        if (tbShowPass.isSelected()) {
            tbShowPass.setText("Hide");
            txtPassword.setEchoChar((char) 0);
        } else {
            tbShowPass.setText("Show");
            txtPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_tbShowPassActionPerformed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        if (evt.getKeyChar() == ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(Login_Form.this, "Spaces are not allowed in the username.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtUsernameKeyPressed

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
            ImageIcon icon = new ImageIcon(Login_Form.class.getResource("/img/Shopaloo-logo.png"));
            Image image = icon.getImage();
            javax.swing.UIManager.put("Frame.icon", image);

            Login_Form login = Login_Form.getInstance();
            login.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername1;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JToggleButton tbShowPass;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
