package UI;

import Databases.DBConnection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Admin_Dashboard extends javax.swing.JFrame {
    private DefaultTableModel productTableModel;

    public Admin_Dashboard() {
        initComponents();
        loadProducts();
    }
    
    private String getLoggedInUserID() {
        return Login_Form.loggedInUserID;
    }
    
    private void loadProducts() {
        DefaultTableModel model = (DefaultTableModel) Table_Products.getModel();
        model.setRowCount(0);

        String loggedInUserId = getLoggedInUserID();

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT p.ProductID, p.Name, p.Price, p.Stocks FROM products p JOIN users u ON p.UserID = u.UserID WHERE u.UserID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, loggedInUserId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String productId = rs.getString("ProductID");
                        String name = rs.getString("Name");
                        double price = rs.getDouble("Price");
                        int stocks = rs.getInt("Stocks");

                        if (name == null) {
                            name = "N/A";
                        }

                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                        String formattedPrice = formatter.format(price);

                        model.addRow(new Object[]{productId, name, formattedPrice, stocks});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }
    
    public void refreshProducts() {
        loadProducts();
    }
    
    private String getProductImage(String productId) {
        String imagePath = "src/product_images/" + productId + ".jpg";
        return imagePath;
    }
    
    private void updateProductDetails(String productId) {
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT Name, Description, UserID, Stocks FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String stocks = rs.getString("Stocks");

                    lblProductName.setText(name);
                    lblProductDesc.setText(description);
                    lblProductImage.setIcon(new ImageIcon(getProductImage(productId))); 
                    lblStocks.setText("Stocks: " + stocks);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving product details: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Products = new javax.swing.JTable();
        lblProducts = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblProductImage = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblProductDesc = new javax.swing.JTextArea();
        lblStocks = new javax.swing.JLabel();
        btnEditProduct = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        btnRemoveProduct = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnViewSales = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        MainPanel.setBackground(new java.awt.Color(248, 248, 248));

        Table_Products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Price", "Stock"
            }
        ));
        Table_Products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Products);

        lblProducts.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        lblProducts.setText("Products");

        lblProductImage.setBackground(new java.awt.Color(255, 255, 255));
        lblProductImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblProductName.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        lblProductName.setText("...");

        lblProductDesc.setColumns(20);
        lblProductDesc.setRows(5);
        lblProductDesc.setToolTipText("");
        lblProductDesc.setWrapStyleWord(true);
        jScrollPane2.setViewportView(lblProductDesc);

        lblStocks.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblStocks.setText("...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStocks)
                    .addComponent(lblProductName)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lblProductName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStocks)
                        .addGap(46, 46, 46))
                    .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEditProduct.setBackground(new java.awt.Color(153, 153, 255));
        btnEditProduct.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnEditProduct.setText("Edit");
        btnEditProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditProduct.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProductActionPerformed(evt);
            }
        });

        btnAddProduct.setBackground(new java.awt.Color(153, 153, 255));
        btnAddProduct.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnAddProduct.setText("Add");
        btnAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddProduct.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        btnRemoveProduct.setBackground(new java.awt.Color(153, 153, 255));
        btnRemoveProduct.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnRemoveProduct.setText("Remove");
        btnRemoveProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoveProduct.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnRemoveProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveProductActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(153, 153, 255));
        btnBack.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png"))); // NOI18N
        btnBack.setText("Log Out");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(MainPanelLayout.createSequentialGroup()
                                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(MainPanelLayout.createSequentialGroup()
                                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(MainPanelLayout.createSequentialGroup()
                                        .addComponent(lblProducts)
                                        .addGap(258, 258, 258)))
                                .addGap(132, 132, 132)
                                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProducts)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Login_Form login = Login_Form.getInstance();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        Edit_Products editForm = new Edit_Products(this);
        editForm.loadProductIDs();
        editForm.setVisible(true);
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        new Add_Products(this).setVisible(true);
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void deleteProductImage(String productId) {
        try {
            // Construct the file path using the ProductID
            java.io.File imageFile = new java.io.File("src/product_images/" + productId + ".jpg");
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    JOptionPane.showMessageDialog(this, "Image file deleted successfully: " + imageFile.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete image file: " + imageFile.getAbsolutePath());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No image file found for ProductID: " + productId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting image file: " + e.getMessage());
        }
    }
    
    private void btnRemoveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveProductActionPerformed
        int selectedRow = Table_Products.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        productTableModel = (DefaultTableModel) Table_Products.getModel();
        String productId = Table_Products.getValueAt(selectedRow, 0).toString();

        String message = "Name: " + lblProductName.getText() + "\n"
                + "Description: " + lblProductDesc.getText() + "\n"
                + "Price: " + Table_Products.getValueAt(selectedRow, 2).toString() + "\n"
                + "Stocks: " + Table_Products.getValueAt(selectedRow, 3).toString() + "\n"
                + "Do you want to proceed?";

        int confirm = JOptionPane.showConfirmDialog(this, message,
                "Confirm Delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DBConnection.Connect()) {
                // Delete the product from the database
                String removeQuery = "DELETE FROM products WHERE ProductID = ?";
                try (PreparedStatement ps = con.prepareStatement(removeQuery)) {
                    ps.setString(1, productId);

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        // Delete the associated product image
                        String imagePath = getProductImage(productId);
                        java.io.File imageFile = new java.io.File(imagePath);
                        if (imageFile.exists() && imageFile.isFile()) {
                            if (imageFile.delete()) {
                                System.out.println("Image file deleted: " + imagePath);
                            } else {
                                System.out.println("Failed to delete image file: " + imagePath);
                            }
                        } else {
                            System.out.println("Image file not found: " + imagePath);
                        }

                        JOptionPane.showMessageDialog(null, "Product removed successfully!", "Removed", JOptionPane.INFORMATION_MESSAGE);
                        productTableModel.removeRow(selectedRow); // Remove the product from the table
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error removing product: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "An error occurred while removing the product.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRemoveProductActionPerformed

    private void btnViewSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSalesActionPerformed
        String loggedInUserId = getLoggedInUserID();
        
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT s.SaleID, p.Name, s.Quantity, s.TotalPrice, s.SaleDate "
                    + "FROM sales s "
                    + "JOIN products p ON s.ProductID = p.ProductID "
                    + "WHERE p.UserID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, loggedInUserId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String saleId = rs.getString("SaleID");
                        String productName = rs.getString("Name");
                        int quantity = rs.getInt("Quantity");
                        double totalPrice = rs.getDouble("TotalPrice");
                        String saleDate = rs.getString("SaleDate");

                        salesModel.addRow(new Object[]{saleId, productName, quantity, totalPrice, saleDate});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading sales: " + e.getMessage());
        }

        // Add the table to a JScrollPane and display it
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(salesTable);
        salesFrame.add(scrollPane);
        salesFrame.setVisible(true);
    }//GEN-LAST:event_btnViewSalesActionPerformed

    private void Table_ProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ProductsMouseClicked
        int row = Table_Products.getSelectedRow();
        if (row != -1) {
            String productId = Table_Products.getValueAt(row, 0).toString();
            updateProductDetails(productId);
        }
    }//GEN-LAST:event_Table_ProductsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTable Table_Products;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnRemoveProduct;
    private javax.swing.JButton btnViewSales;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea lblProductDesc;
    private javax.swing.JLabel lblProductImage;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblStocks;
    // End of variables declaration//GEN-END:variables
}
