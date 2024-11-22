package UI;

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class Customer_Dashboard extends javax.swing.JFrame {
    private DefaultTableModel productTableModel, cartTableModel;

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
            String query = "SELECT p.ProductID, p.Name, p.Price, u.Username FROM products p JOIN users u ON p.SupplierID = u.UserID WHERE u.UserID = 'Supplier'";
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

                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                    String formattedPrice = formatter.format(price);

                    productTableModel.addRow(new Object[]{productId, name, formattedPrice, supplierName});
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
                    lblQuantity.setText("1");
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
    
    private double calculateTotal() {
        double total = 0.0;

        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            int quantity = (int) cartTableModel.getValueAt(i, 2);
            String priceString = cartTableModel.getValueAt(i, 3).toString();

            double price = Double.parseDouble(priceString.replace("PHP ", "").replace(",", "").trim());

            total += quantity * price;
        }
        return total;
    }
    
    private void updateTotal() {
        double total = calculateTotal();

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        lblTotal.setText(formatter.format(total));
    }
    
    private String getLoggedInUserID() {
        return Login_Form.loggedInUserID;
    }
    
    private void setColumnWidths(JTable sourceTable, JTable targetTable) {
        for (int i = 0; i < sourceTable.getColumnCount(); i++) {
            targetTable.getColumnModel().getColumn(i).setPreferredWidth(sourceTable.getColumnModel().getColumn(i).getPreferredWidth());
        }
    }
    
    private void insertOrderIntoDatabase(String userID, String modeOfPayment, String cardNumber, String cardHolder, String expiryDate, String cvc) {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425"; 
        String dbUser    = "root"; 
        String dbPassword = ""; 

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String insertOrderQuery = "INSERT INTO orders (UserID, ProductID, Quantity, Price, Status, ModeOfPayment) VALUES (?, ?, ?, ?, ?, ?)";

            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String productId = cartTableModel.getValueAt(i, 0).toString();
                int quantity = (int) cartTableModel.getValueAt(i, 2);
                String priceString = cartTableModel.getValueAt(i, 3).toString(); 
                double price = quantity * Double.parseDouble(priceString.replace("PHP ", "").replace(",", "").trim()); 
                String status = "Pending";

                try (PreparedStatement ps = con.prepareStatement(insertOrderQuery)) {
                    ps.setString(1, userID);
                    ps.setString(2, productId);
                    ps.setInt(3, quantity);
                    ps.setDouble(4, price);
                    ps.setString(5, status);
                    ps.setString(6, modeOfPayment);
                    ps.executeUpdate();
                }
            }

            cartTableModel.setRowCount(0); 
            updateTotal();
            JOptionPane.showMessageDialog(null, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Error during checkout: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "An error occurred while placing the order. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); 
        }
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
        btnBack = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        comboModeOfPayment = new javax.swing.JComboBox<>();
        Total1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        MainPanel.setBackground(new java.awt.Color(248, 248, 248));

        Table_ShoppingCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        lblProductName.setText("...");

        lblSupplierName.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblSupplierName.setText("...");

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

        lblQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuantity.setText("1");

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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSupplierName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnPlusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblQuantity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMinusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblProductName)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPlusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuantity)
                            .addComponent(btnMinusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addContainerGap())
        );

        Total.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Total.setText("Total");

        comboModeOfPayment.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        comboModeOfPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash On Delivery", "Card Payment", "GCash" }));

        Total1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Total1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Total1.setText("Mode of Payment");

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCheckOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblShoppingCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(MainPanelLayout.createSequentialGroup()
                                .addComponent(Total)
                                .addGap(114, 114, 114)))
                        .addGap(18, 18, 18)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboModeOfPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Total1))))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Total)
                            .addComponent(Total1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboModeOfPayment))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        String loggedInUserID = getLoggedInUserID();
        String modeOfPayment = (String) comboModeOfPayment.getSelectedItem();

        if (loggedInUserID == null || loggedInUserID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User  not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cartTableModel = (DefaultTableModel) Table_ShoppingCart.getModel();

        if (cartTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Your shopping cart is empty. Please add products before checking out.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = 0.0;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            String priceString = cartTableModel.getValueAt(i, 3).toString(); 
            double price = Double.parseDouble(priceString.replace("PHP ", "").replace(",", "").trim());
            int quantity = (int) cartTableModel.getValueAt(i, 2);
            total += price * quantity;
        }

        if ("Card Payment".equals(modeOfPayment)) {
            CardPayment cardPayment = new CardPayment(total);
            cardPayment.setVisible(true);

            cardPayment.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    String cardNumber = cardPayment.getCardNumber();
                    String cardHolder = cardPayment.getCardHolder();
                    String expiryDate = cardPayment.getExpiryDate();
                    String cvc = cardPayment.getCVC();

                    insertOrderIntoDatabase(loggedInUserID, modeOfPayment, cardNumber, cardHolder, expiryDate, cvc);
                }
            });
        } else {
            insertOrderIntoDatabase(loggedInUserID, modeOfPayment, null, null, null, null);
        }
    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Login_Form login = Login_Form.getInstance();
        login.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        int row = Table_Products.getSelectedRow(); 
        if (row != -1) { 
            String productId = Table_Products.getValueAt(row, 0).toString();
            String productName = Table_Products.getValueAt(row, 1).toString();
            String priceString = Table_Products.getValueAt(row, 2).toString();

            System.out.println("Raw price string: " + priceString);

            double price;
            try {
                priceString = priceString.replaceAll("[^\\d.]", "");
                price = Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid price format: " + priceString, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(lblQuantity.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid quantity value!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cartTableModel = (DefaultTableModel) Table_ShoppingCart.getModel();
            boolean productExists = false;

            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                    int existingQuantity = (int) cartTableModel.getValueAt(i, 2);
                    cartTableModel.setValueAt(existingQuantity + quantity, i, 2);
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                cartTableModel.addRow(new Object[]{productId, productName, quantity, price});
            }

            updateTotal();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to add to cart.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        setColumnWidths(Table_Products, Table_ShoppingCart);
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void btnPlusQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusQuantityActionPerformed
        int quantity = Integer.parseInt(lblQuantity.getText());
        quantity += 1;
        lblQuantity.setText(Integer.toString(quantity));
    }//GEN-LAST:event_btnPlusQuantityActionPerformed

    private void btnMinusQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusQuantityActionPerformed
        int quantity = Integer.parseInt(lblQuantity.getText());
        
        if ((quantity - 1) == 0) {
            lblQuantity.setText("1");
        } else {
            quantity -= 1;
            lblQuantity.setText(Integer.toString(quantity));
        }
    }//GEN-LAST:event_btnMinusQuantityActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTable Table_Products;
    private javax.swing.JTable Table_ShoppingCart;
    private javax.swing.JLabel Total;
    private javax.swing.JLabel Total1;
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JButton btnMinusQuantity;
    private javax.swing.JButton btnPlusQuantity;
    private javax.swing.JComboBox<String> comboModeOfPayment;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea lblProductDesc;
    private javax.swing.JLabel lblProductImage;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblShoppingCart;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
}
