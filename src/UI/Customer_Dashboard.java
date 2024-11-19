package UI;

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;

public class Customer_Dashboard extends javax.swing.JFrame {
    private DefaultTableModel productTableModel;

    public Customer_Dashboard() {
        initComponents();
        loadProducts();
        addTableMouseListener();
    }

    private void loadProducts() {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser  = "root";
        String dbPassword = "";

        productTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Seller"}, 0);
        Table_Products.setModel(productTableModel);

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String query = "SELECT p.ProductID, p.Name, p.Price, u.Username FROM products p JOIN users u ON p.SupplierID = u.UserID WHERE u.Role = 'Supplier'";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String productId = rs.getString("ProductID");
                    String name = rs.getString("Name");
                    double price = rs.getDouble("Price");
                    String supplierName = rs.getString("Username");                        
                   
                    if (name == null) {
                        name = "N/A";
                    }
                    
                    if (supplierName == null) {
                        supplierName = "N/A";
                    }

                    productTableModel.addRow(new Object[]{productId, name, "PHP " + price, supplierName});
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private void addTableMouseListener() {
        Table_Products.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = Table_Products.getSelectedRow();
                if (row != -1) {
                    String productId = Table_Products.getValueAt(row, 0).toString();
                    updateProductDetails(productId);
                }
            }
        });
    }

    private void updateProductDetails(String productId) {
        // Get product details from the database
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser  = "root";
        String dbPassword = "";

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String query = "SELECT Name, Description, SupplierID FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String supplierId = rs.getString("SupplierID");

                    lblProductName.setText(name);
                    lblProductDesc.setText(description);
                    lblProductImage.setIcon(new ImageIcon(getProductImage(productId))); 
                    lblSupplierName.setText("Seller: " + getSupplierName(supplierId));
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving product details: " + e.getMessage());
        }
    }

    private String getSupplierName(String supplierId) {
        String supplierName = "";
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser  = "root";
        String dbPassword = "";

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String query = "SELECT Username FROM users WHERE UserID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, supplierId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    supplierName = rs.getString("Username");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving supplier name: " + e.getMessage());
        }
        return supplierName;
    }

    private String getProductImage(String productId) {
        String imagePath = "src/product_images/" + productId + ".jpg";
        return imagePath;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_ShoppingCart = new javax.swing.JTable();
        lblShoppingCart = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblProductImage = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        lblSupplierName = new javax.swing.JLabel();
        btnPlusQuantity = new javax.swing.JButton();
        btnMinusQuantity = new javax.swing.JButton();
        lblQuantity = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Products = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblProductDesc = new javax.swing.JTextArea();
        btnCheckOut = new javax.swing.JButton();
        btnAddToCart = new javax.swing.JButton();
        btnRemoveFromCart = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        MainPanel.setBackground(new java.awt.Color(248, 248, 248));

        Table_ShoppingCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Quantity", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table_ShoppingCart);

        lblShoppingCart.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        lblShoppingCart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShoppingCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Cart.png"))); // NOI18N
        lblShoppingCart.setText("Shopping Cart");

        lblProductImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblProductName.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N

        lblSupplierName.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N

        btnPlusQuantity.setBackground(new java.awt.Color(153, 153, 255));
        btnPlusQuantity.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnPlusQuantity.setText("+");
        btnPlusQuantity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlusQuantity.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnPlusQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusQuantityActionPerformed(evt);
            }
        });

        btnMinusQuantity.setBackground(new java.awt.Color(153, 153, 255));
        btnMinusQuantity.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnMinusQuantity.setText("-");
        btnMinusQuantity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinusQuantity.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnMinusQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinusQuantityActionPerformed(evt);
            }
        });

        lblQuantity.setText("Quantity");

        Table_Products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Price", "Seller"
            }
        ));
        jScrollPane3.setViewportView(Table_Products);

        lblProductDesc.setColumns(20);
        lblProductDesc.setRows(5);
        lblProductDesc.setToolTipText("");
        lblProductDesc.setWrapStyleWord(true);
        jScrollPane2.setViewportView(lblProductDesc);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProductName)
                                    .addComponent(lblSupplierName)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnPlusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblQuantity)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnMinusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lblProductName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSupplierName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPlusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuantity)
                            .addComponent(btnMinusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        btnCheckOut.setBackground(new java.awt.Color(153, 153, 255));
        btnCheckOut.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        btnCheckOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/CheckOut.png"))); // NOI18N
        btnCheckOut.setText("Check Out");
        btnCheckOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCheckOut.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnCheckOut.setIconTextGap(10);
        btnCheckOut.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOutActionPerformed(evt);
            }
        });

        btnAddToCart.setBackground(new java.awt.Color(153, 153, 255));
        btnAddToCart.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnAddToCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/AddToCart.png"))); // NOI18N
        btnAddToCart.setText("Add To Cart");
        btnAddToCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddToCart.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        btnRemoveFromCart.setBackground(new java.awt.Color(153, 153, 255));
        btnRemoveFromCart.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnRemoveFromCart.setText("Remove");
        btnRemoveFromCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoveFromCart.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnRemoveFromCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromCartActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCheckOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblShoppingCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoveFromCart, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblShoppingCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoveFromCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOutActionPerformed
        
    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Login_Form login = Login_Form.getInstance();
        login.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void btnRemoveFromCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromCartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveFromCartActionPerformed

    private void btnPlusQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlusQuantityActionPerformed

    private void btnMinusQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMinusQuantityActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTable Table_Products;
    private javax.swing.JTable Table_ShoppingCart;
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JButton btnMinusQuantity;
    private javax.swing.JButton btnPlusQuantity;
    private javax.swing.JButton btnRemoveFromCart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea lblProductDesc;
    private javax.swing.JLabel lblProductImage;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblShoppingCart;
    private javax.swing.JLabel lblSupplierName;
    // End of variables declaration//GEN-END:variables
}
