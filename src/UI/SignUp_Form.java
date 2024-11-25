package UI;

import Databases.DBConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUp_Form extends javax.swing.JFrame {
    public SignUp_Form() {
        initComponents();
        setIcon();
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUsername1 = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnSignUp = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        lblPassword1 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtPassword1 = new javax.swing.JPasswordField();
        lblPassword2 = new javax.swing.JLabel();
        lblPassword3 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(211, 211, 211));
        jPanel1.setPreferredSize(new java.awt.Dimension(275, 550));

        lblUsername1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblUsername1.setText("Username");

        lblPassword.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblPassword.setText("Password");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnSignUp.setBackground(new java.awt.Color(153, 153, 255));
        btnSignUp.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnSignUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sign-up.png"))); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignUp.setIconTextGap(10);
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

        lblPassword1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblPassword1.setText("First Name");

        txtFirstName.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        txtPassword1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblPassword2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblPassword2.setText("Re-Enter Password");

        lblPassword3.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblPassword3.setText("Last Name");

        txtLastName.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsername1))
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPassword1)
                                    .addComponent(lblPassword2)
                                    .addComponent(lblPassword)
                                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblPassword3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(txtLastName)))
                            .addComponent(txtPassword1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblUsername1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPassword2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPassword1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPassword3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        btnBack.setBackground(new java.awt.Color(153, 153, 255));
        btnBack.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png"))); // NOI18N
        btnBack.setText("Go Back");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblWelcome.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADMIN\\Downloads\\SHOP-A-LOO (1) (2).png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblWelcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String password1 = new String(txtPassword1.getPassword());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String fullName = firstName + " " + lastName;

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(password1)) {
            JOptionPane.showMessageDialog(this, "Passwords don't match.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String insertUserQuery = "INSERT INTO users (Username, Password, Role, FullName) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.Connect();
            PreparedStatement ps = con.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, "Customer");
            ps.setString(4, fullName);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                JOptionPane.showMessageDialog(this, "User  registered successfully!");
                this.dispose();
                Login_Form login = Login_Form.getInstance();
                login.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Login_Form login = Login_Form.getInstance();
        login.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        if (evt.getKeyChar() == ' ') {
            evt.consume();
            JOptionPane.showMessageDialog(SignUp_Form.this, "Spaces are not allowed in the username.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtUsernameKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword1;
    private javax.swing.JLabel lblPassword2;
    private javax.swing.JLabel lblPassword3;
    private javax.swing.JLabel lblUsername1;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
