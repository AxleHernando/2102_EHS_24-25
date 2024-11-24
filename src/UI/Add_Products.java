package UI;

import Databases.DBConnection;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

public class Add_Products extends javax.swing.JFrame {
    private Admin_Dashboard adminDashboard;
    private String nextProductID = "1";
    private java.io.File selectedFile;

    public Add_Products(Admin_Dashboard adminDashboard) {
        initComponents();
        this.adminDashboard = adminDashboard;
        txtFilePath.setEditable(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnViewSales = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();
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

        btnViewSales.setBackground(new java.awt.Color(153, 153, 255));
        btnViewSales.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnViewSales.setText("View Sales");
        btnViewSales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewSales.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnViewSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSalesActionPerformed(evt);
            }
        });

        lblWelcome.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Product Details");

        txtName.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel1.setText("Product Name");

        txtDesc.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel2.setText("Description");

        txtPrice.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel3.setText("Price");

        jLabel4.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel4.setText("Stocks");

        txtStocks.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N

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

        txtFilePath.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        txtFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilePathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblWelcome)
                .addGap(70, 70, 70))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFilePath))
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtPrice))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtStocks, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtDesc)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblWelcome)
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStocks, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFilePath)
                    .addComponent(btnAddImage, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSalesActionPerformed

    }//GEN-LAST:event_btnViewSalesActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtStocks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
        txtFilePath.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));

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

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select an image for the product.");
            isValid = false;
        }

        if (isValid) {
            try (Connection con = DBConnection.Connect()) {
                String query = "INSERT INTO products (Name, Description, Price, Stocks, UserID) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, txtName.getText());
                    ps.setString(2, txtDesc.getText());
                    ps.setDouble(3, Double.parseDouble(txtPrice.getText()));
                    ps.setInt(4, Integer.parseInt(txtStocks.getText()));
                    ps.setString(5, Login_Form.loggedInUserID);

                NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                    String formattedPrice = formatter.format(Double.parseDouble(txtPrice.getText()));

                    String message = "Name: " + txtName.getText() + "\n"
                            + "Description: " + txtDesc.getText() + "\n"
                            + "Price: " + formattedPrice + "\n"
                            + "Stocks: " + txtStocks.getText() + "\n"
                            + "Do you want to proceed?";
                    int confirm = JOptionPane.showConfirmDialog(this, message,
                            "Confirm Checkout", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        int rowsAffected = ps.executeUpdate();

                        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int productId = generatedKeys.getInt(1);
                                String productIDString = String.valueOf(productId);

                                saveFileToProjectFolder(selectedFile, productIDString);

                                String imagePath = "src/product_images/" + productIDString + ".jpg";
                                txtFilePath.setText(imagePath);

                                JOptionPane.showMessageDialog(this, "Product added successfully!");
                                if (adminDashboard != null) {
                                    adminDashboard.refreshProducts();
                                }
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to add product.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Adding Products Canceled.",
                                "Cancellation", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception e) {
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
 
            Image resizedImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            BufferedImage bufferedResizedImage = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = bufferedResizedImage.createGraphics();
            g.drawImage(resizedImage, 0, 0, null);
            g.dispose();

            java.io.File destFile = new java.io.File(destFolder, productId + ".jpg");
            java.nio.file.Files.copy(
                    file.toPath(),
                    destFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
            JOptionPane.showMessageDialog(this, "Image saved as: " + destFile.getAbsolutePath());
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }
    
    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == javax.swing.JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile(); // Save the selected file globally
            txtFilePath.setText(selectedFile.getAbsolutePath());  // Set file path temporarily
        }
    }//GEN-LAST:event_btnAddImageActionPerformed

    private void txtFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFilePathActionPerformed
        
    }//GEN-LAST:event_txtFilePathActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Add_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add_Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Admin_Dashboard adminDashboard = new Admin_Dashboard(); // Mock instance
                new Add_Products(adminDashboard).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImage;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnViewSales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtFilePath;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtStocks;
    // End of variables declaration//GEN-END:variables
}
