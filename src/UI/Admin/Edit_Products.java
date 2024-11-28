package UI.Admin;

import Databases.DBConnection;
import Objects.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;

public class Edit_Products extends javax.swing.JFrame {
    private final Admin_Dashboard adminDashboard;
    private final String selectedProductID;
    private boolean productUpdated;
    private String stockNotes;

    public Edit_Products(Admin_Dashboard adminDashboard, String productID) {
        initComponents();
        this.adminDashboard = adminDashboard;
        this.selectedProductID = productID;
        loadProductDetails(selectedProductID);
        setIcon();
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }
    
    private String getProductID() {
        return this.selectedProductID;
    }
    
    private void loadProductDetails(String productID) {
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT * FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        txtSupplier.setText(rs.getString("SupplierName"));
                        txtName.setText(rs.getString("Name"));
                        txtDesc.setText(rs.getString("Description"));
                        txtPrice.setText(String.valueOf(rs.getDouble("Price")));
                        txtStocks.setText(String.valueOf(rs.getInt("Stocks")));
                        txtCategory.setText(rs.getString("Category"));
                        displayProductImageFilePath(productID);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void displayProductImageFilePath(String productId) {
        String imagePath = "src/product_images/" + productId + ".jpg";
        java.io.File imageFile = new java.io.File(imagePath);
        if (imageFile.exists()) {
            txtFilePath.setText(imageFile.getAbsolutePath());
        } else {
            txtFilePath.setText("No image available.");
        }
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
        txtCategory = new javax.swing.JTextField();
        lblCategory = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSupplier = new javax.swing.JTextField();

        setTitle("Edit Products");
        setPreferredSize(new java.awt.Dimension(330, 470));
        setResizable(false);
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
        btnSubmit.setText("Save Changes");
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
        btnAddImage.setText("Change");
        btnAddImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddImage.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImageActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 321, 100, 40));

        txtFilePath.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        txtFilePath.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFilePath.setPreferredSize(new java.awt.Dimension(174, 19));
        txtFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilePathActionPerformed(evt);
            }
        });
        getContentPane().add(txtFilePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 321, -1, 40));

        txtCategory.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 276, 280, 33));

        lblCategory.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        lblCategory.setText("Category");
        getContentPane().add(lblCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 257, -1, -1));

        jLabel5.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel5.setText("Supplier Name");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 19, -1, -1));

        txtSupplier.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        getContentPane().add(txtSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 38, 280, 36));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        String selectedID = getProductID();

        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtStocks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtCategory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
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

        if (txtCategory.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category field is required.");
            txtCategory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        }
        
        if (txtSupplier.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Supplier Name field is required.");
            txtSupplier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
            isValid = false;
        }

        if (selectedID == null) {
            JOptionPane.showMessageDialog(this, "Please select a product to update.");
            isValid = false;
        }

        if (isValid) {
            try (Connection con = DBConnection.Connect()) {
                String stockQuery = "SELECT Stocks FROM products WHERE ProductID = ?";
                String query = "UPDATE products SET Name = ?, Description = ?, Price = ?, Stocks = ?, Category = ?, SupplierName = ? WHERE ProductID = ?";
                
                String supplier = txtSupplier.getText();
                String product = txtName.getText();
                String desc = txtDesc.getText();
                double price = Double.parseDouble(txtPrice.getText());
                int stocks = Integer.parseInt(txtStocks.getText());
                String category = txtCategory.getText();
                
                try (PreparedStatement psStock = con.prepareStatement(stockQuery)) {
                    psStock.setString(1, getProductID());
                    try (ResultSet rsStock = psStock.executeQuery()) {
                        if (rsStock.next()) {
                            int currentStock = rsStock.getInt("Stocks");
                            if (currentStock > stocks) {
                                setStockNotes("Stock reduced: " + (currentStock - stocks));
                            } else if (currentStock < stocks) {
                                setStockNotes ("Stock added: " + (stocks - currentStock));
                            }
                        }
                    }
                }
                
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, product);
                    ps.setString(2, desc);
                    ps.setDouble(3, price);
                    ps.setInt(4, stocks);
                    ps.setString(5, category);
                    ps.setString(6, supplier);
                    ps.setString(7, selectedID);

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
                        
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Product updated successfully!");
                            adminDashboard.refreshProducts();
                            setProductUpdated(true);
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update product.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Updating product canceled.",
                                "Cancellation", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    public boolean isProductUpdated() {
        return productUpdated;
    }
    
    public void setProductUpdated(boolean productUpdated) {
        this.productUpdated = productUpdated;
        System.out.println("Product updated flag set to: " + productUpdated);
    }
    
    public String getStockNotes() {
        return stockNotes;
    }
    
    public void setStockNotes(String notes) {
        this.stockNotes = notes;
    }
    
    private void saveFileToProjectFolder(java.io.File file, String productId) {
        try {
            java.io.File destFolder = new java.io.File("src/product_images");
            if (!destFolder.exists()) {
                destFolder.mkdirs();
            }
            
            java.io.File destFile = new java.io.File(destFolder, productId + ".jpg");
            java.nio.file.Files.copy(
                    file.toPath(),
                    destFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
            JOptionPane.showMessageDialog(this, "Image updated successfully!");
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }
    
    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getAbsolutePath());
            saveFileToProjectFolder(selectedFile, getProductID());
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
    private javax.swing.JLabel lblCategory;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtFilePath;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtStocks;
    private javax.swing.JTextField txtSupplier;
    // End of variables declaration//GEN-END:variables
}
