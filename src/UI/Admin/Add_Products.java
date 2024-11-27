package UI.Admin;

import Databases.DBConnection;
import Objects.UserSession;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;

public class Add_Products extends javax.swing.JFrame {
    private final Admin_Dashboard adminDashboard;
    private java.io.File selectedFile;

    public Add_Products(Admin_Dashboard adminDashboard) {
        initComponents();
        this.adminDashboard = adminDashboard;
        txtFilePath.setEditable(false);
        setIcon();
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtStocks = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnAddImage = new javax.swing.JButton();
        txtFilePath = new javax.swing.JTextField();
        lblCategory1 = new javax.swing.JLabel();
        txtCat = new javax.swing.JTextField();
        txtSupplier = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setTitle("Add Products");
        setMinimumSize(new java.awt.Dimension(320, 430));
        setPreferredSize(new java.awt.Dimension(340, 470));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtName.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 99, 280, 36));

        jLabel1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel1.setText("Product Name");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 80, -1, -1));

        txtDesc.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 160, 280, 33));

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel2.setText("Description");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 141, -1, -1));

        txtPrice.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 218, 202, 33));

        jLabel3.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel3.setText("Price");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 199, -1, -1));

        jLabel4.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel4.setText("Stocks");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 199, -1, -1));

        txtStocks.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtStocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 218, 60, 33));

        btnSubmit.setBackground(new java.awt.Color(153, 153, 255));
        btnSubmit.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubmit.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 373, 280, 40));

        btnAddImage.setBackground(new java.awt.Color(153, 153, 255));
        btnAddImage.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnAddImage.setText("Add Image");
        btnAddImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddImage.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImageActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 321, 100, 40));

        txtFilePath.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        txtFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilePathActionPerformed(evt);
            }
        });
        getContentPane().add(txtFilePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 321, 174, 40));

        lblCategory1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblCategory1.setText("Category");
        getContentPane().add(lblCategory1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 257, -1, -1));

        txtCat.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 276, 280, 33));

        txtSupplier.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 38, 280, 36));

        jLabel5.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel5.setText("Supplier Name");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 19, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtStocks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtFilePath.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtSupplier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));

        boolean isValid = true;

        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name field is required.");
            txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        }

        if (txtDesc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description field is required.");
            txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        }

        if (txtPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price field is required.");
            txtPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        } else {
            try {
                Double.parseDouble(txtPrice.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Price must be a number.");
                txtPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
                isValid = false;
            }
        }

        if (txtStocks.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stocks field is required.");
            txtStocks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        } else {
            try {
                Integer.parseInt(txtStocks.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stocks must be a whole number.");
                txtStocks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
                isValid = false;
            }
        }
        
        if (txtCat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category field is required.");
            txtCat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0), 2));
            isValid = false;
        }
        
        if (txtSupplier.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Supplier Name field is required.");
            txtCat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        }

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select an image for the product.");
            isValid = false;
        }

        if (isValid) {
            try (Connection con = DBConnection.Connect()) {
                String query = "INSERT INTO products (Name, Description, Price, Stocks, Category, SupplierName) VALUES (?, ?, ?, ?, ?, ?)";
                
                String supplier = txtSupplier.getText();
                String product = txtName.getText();
                String desc = txtDesc.getText();
                double price = Double.parseDouble(txtPrice.getText());
                int stocks = Integer.parseInt(txtStocks.getText());
                String category = txtCat.getText();
                
                try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, product);
                    ps.setString(2, desc);
                    ps.setDouble(3, price);
                    ps.setInt(4, stocks);
                    ps.setString(5, category);
                    ps.setString(6, supplier);
                    
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                    String formattedPrice = formatter.format(Double.parseDouble(txtPrice.getText()));
                    
                    String message = "Supplier Name: " + supplier + "\n"
                            + "Product Name: " + product + "\n"
                            + "Description: " + desc + "\n"
                            + "Price: " + formattedPrice + "\n"
                            + "Stocks: " + stocks + "\n"
                            + "Category: " + category + "\n"
                            + "Do you want to proceed?";
                    int confirm = JOptionPane.showConfirmDialog(this, message,
                            "Confirm Update", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        int rowsAffected = ps.executeUpdate();
                        
                        String queryLogs = "INSERT INTO user_logs (UserID, FullName, Role, Action, Date, Time, Notes) VALUES (?, ?, ?, ?, "
                                + "STR_TO_DATE(DATE_FORMAT(CURDATE(), '%m/%d/%Y'), '%m/%d/%Y'), "
                                + "DATE_FORMAT(NOW(), '%H:%i:%s'), ?)";
                        try (PreparedStatement psLogs = con.prepareStatement(queryLogs)) {
                            String userID = UserSession.getCurrentUserID();
                            String queryUser = "SELECT * FROM users WHERE UserID = ?";
                            try (PreparedStatement psUser = con.prepareStatement(queryUser)) {
                                psUser.setString(1, userID);
                                ResultSet rs = psUser.executeQuery();
                                if (rs.next()) {
                                    String fullName = rs.getString("FullName");
                                    String role = rs.getString("Role");

                                    psLogs.setString(1, userID);
                                    psLogs.setString(2, fullName);
                                    psLogs.setString(3, role);
                                    psLogs.setString(4, "Add Product");
                                    psLogs.setString(5, fullName + " Added a new Product:\n\n" 
                                            + "Supplier Name: " + supplier + "\n"
                                            + "Product: " + product + "\n"
                                            + "Desciption: " + desc + "\n"
                                            + "Price: " + formattedPrice + "\n"
                                            + "Stocks: " + stocks + "\n"
                                            + "Category: " + category);
                                    psLogs.executeUpdate();
                                }
                            }
                        }
                        
                        if (rowsAffected > 0) {
                            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int productId = generatedKeys.getInt(1);
                                    String productIDString = String.valueOf(productId);
                                    saveFileToProjectFolder(selectedFile, productIDString);
                                    JOptionPane.showMessageDialog(this, "Product added successfully!");
                                    adminDashboard.refreshProducts();
                                    this.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Failed to retrieve product ID.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "No rows affected. Insert failed.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Adding product canceled.",
                                "Cancellation", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void saveFileToProjectFolder(java.io.File file, String productId) {
        try {
            java.io.File destFolder = new java.io.File("src/product_images");
            if (!destFolder.exists()) {
                destFolder.mkdirs();
            }
            BufferedImage originalImage = ImageIO.read(file);

            if (originalImage.getWidth() == 250 && originalImage.getHeight() == 250) {
                java.io.File destFile = new java.io.File(destFolder, productId + ".jpg");
                ImageIO.write(originalImage, "jpg", destFile);
            } else {
                Image resizedImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                BufferedImage bufferedResizedImage = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);

                Graphics2D g = bufferedResizedImage.createGraphics();
                g.drawImage(resizedImage, 0, 0, null);
                g.dispose();

                java.io.File destFile = new java.io.File(destFolder, productId + ".jpg");
                ImageIO.write(bufferedResizedImage, "jpg", destFile);
            }
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }
    
    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == javax.swing.JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getAbsolutePath()); 
        }
    }//GEN-LAST:event_btnAddImageActionPerformed

    private void txtFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFilePathActionPerformed
        
    }//GEN-LAST:event_txtFilePathActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImage;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblCategory1;
    private javax.swing.JTextField txtCat;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtFilePath;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtStocks;
    private javax.swing.JTextField txtSupplier;
    // End of variables declaration//GEN-END:variables
}
