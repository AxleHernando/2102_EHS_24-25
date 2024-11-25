package UI;

import Databases.DBConnection;
import java.awt.event.KeyEvent;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class Customer_Dashboard extends javax.swing.JFrame {
    private DefaultTableModel productTableModel, cartTableModel;
    private String productId;

    public Customer_Dashboard() {
        initComponents();
        loadProducts();
    }

    private void loadProducts() {
        productTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Seller", "Category"}, 0);
        Table_Products.setModel(productTableModel);

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT ProductID, Name, Price, SupplierName, Category FROM products";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String productId = rs.getString("ProductID");
                    String name = rs.getString("Name");
                    double price = rs.getDouble("Price");
                    String fullName = rs.getString("SupplierName");
                    String category = rs.getString("Category");

                    if (name == null || name.isEmpty()) {
                        name = "N/A";
                    }

                    if (fullName == null || fullName.isEmpty()) {
                        fullName = "N/A";
                    }
                    
                    updateProductDetails(productId);
                    
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                    String formattedPrice = formatter.format(price);

                    productTableModel.addRow(new Object[]{productId, name, formattedPrice, fullName, category});
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private void updateProductDetails(String productId) {
        this.productId = productId;
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT Name, Description, SupplierName FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String supplier = rs.getString("SupplierName");

                    lblProductName.setText(name);
                    lblProductDesc.setText(description);
                    lblSupplierName.setText("Seller: " + supplier);
                    txtQuantity.setText("1");
                    btnMinusQuantity.setEnabled(false);
                    
                    lblProductImage.setIcon(null);

                    ImageIcon productImage = new ImageIcon(getProductImage(productId));
                    lblProductImage.setIcon(productImage);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving product details: " + e.getMessage());
        }
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
    
    private void searchProducts() {
        String searchText = txtSearch.getText().toLowerCase();
        productTableModel.setRowCount(0);

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT ProductID, Name, Price, SupplierName, Category "
                    + "FROM products "
                    + "WHERE LOWER(Name) LIKE ? OR LOWER(SupplierName) LIKE ? OR LOWER(ProductID) LIKE ? OR LOWER(Category) LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                String searchPattern = "%" + searchText + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String productId = rs.getString("ProductID");
                        String name = rs.getString("Name");
                        double price = rs.getDouble("Price");
                        String fullName = rs.getString("SupplierName");
                        String category = rs.getString("Category");

                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                        String formattedPrice = formatter.format(price);

                        productTableModel.addRow(new Object[]{productId, name, formattedPrice, fullName, category});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getProductCategory(String productId) {
        String category = "";

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT Category FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    category = rs.getString("Category");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving product category: " + e.getMessage());
        }
        return category;
    }
    
    private int getCurrentStock(String productId) {
        int stock = 0;
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT Stocks FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    stock = rs.getInt("Stocks");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving stock: " + e.getMessage());
        }
        return stock;
    }
    
    private void insertOrderIntoDatabase(String userID, String modeOfPayment) {
        try (Connection con = DBConnection.Connect()) {
            String insertOrderQuery = "INSERT INTO orders (UserID, ProductID, Quantity, Price, ModeOfPayment, Category) VALUES (?, ?, ?, ?, ?, ?)";
            String updateStockQuery = "UPDATE products SET Stocks = Stocks - ? WHERE ProductID = ?";

            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String productId = cartTableModel.getValueAt(i, 0).toString();
                int quantity = (int) cartTableModel.getValueAt(i, 2);
                String priceString = cartTableModel.getValueAt(i, 3).toString();
                double price = quantity * Double.parseDouble(priceString.replace("PHP ", "").replace(",", "").trim());
                String category = getProductCategory(productId);

                int currentStock = getCurrentStock(productId);

                if (currentStock < quantity) {
                    JOptionPane.showMessageDialog(null, "The product " + productId + " does not have enough stock.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(insertOrderQuery)) {
                    ps.setString(1, userID);
                    ps.setString(2, productId);
                    ps.setInt(3, quantity);
                    ps.setDouble(4, price);
                    ps.setString(5, modeOfPayment);
                    ps.setString(6, category);
                    ps.executeUpdate();
                }

                try (PreparedStatement psUpdate = con.prepareStatement(updateStockQuery)) {
                    psUpdate.setInt(1, quantity);
                    psUpdate.setString(2, productId);
                    psUpdate.executeUpdate();
                }
            }

            cartTableModel.setRowCount(0);
            updateTotal();
            JOptionPane.showMessageDialog(null, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while placing the order. Please try again. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void insertCashIntoDatabase(String userID, String cashTendered) {
        try (Connection con = DBConnection.Connect()) {
            String insertCashQuery = "INSERT INTO cashPayment (UserID, CashTendered) VALUES (?, ?)";
            
            try (PreparedStatement ps = con.prepareStatement(insertCashQuery)) {
                ps.setString(1, userID);
                ps.setString(2, cashTendered);
                ps.executeUpdate();
            }
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
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Products = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblProductDesc = new javax.swing.JTextArea();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtQuantity = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        btnCheckOut = new javax.swing.JButton();
        btnAddToCart = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        comboModeOfPayment = new javax.swing.JComboBox<>();
        Total1 = new javax.swing.JLabel();
        btnRemoveProduct = new javax.swing.JButton();
        btnPlusQuantityCart = new javax.swing.JButton();
        txtQuantityCart = new javax.swing.JTextField();
        btnMinusQuantityCart = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Shopaloo Dashboard");
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
        Table_ShoppingCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ShoppingCartMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_ShoppingCart);

        lblShoppingCart.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        lblShoppingCart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShoppingCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Cart.png"))); // NOI18N
        lblShoppingCart.setText("Shopping Cart");

        lblProductImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        Table_Products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Price", "Seller", "Category"
            }
        ));
        Table_Products.setName(""); // NOI18N
        Table_Products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ProductsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table_Products);

        lblProductDesc.setColumns(20);
        lblProductDesc.setRows(5);
        lblProductDesc.setToolTipText("");
        lblProductDesc.setWrapStyleWord(true);
        lblProductDesc.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        lblProductDesc.setEnabled(false);
        jScrollPane2.setViewportView(lblProductDesc);

        txtSearch.setText("Search");
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtSearchMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtSearchMouseReleased(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Search.png"))); // NOI18N
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtQuantity.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        txtQuantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantity.setText("1");

        btnRefresh.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        btnRefresh.setText("REFRESH");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSupplierName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnPlusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMinusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblProductName)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRefresh))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSearch)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSearch))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(lblProductName))
                            .addComponent(btnRefresh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSupplierName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPlusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMinusQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        btnBack.setText("Log Out");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        comboModeOfPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash On Delivery", "Card Payment" }));

        Total1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Total1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Total1.setText("Mode of Payment");

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

        btnPlusQuantityCart.setBackground(new java.awt.Color(153, 153, 255));
        btnPlusQuantityCart.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnPlusQuantityCart.setText("+");
        btnPlusQuantityCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlusQuantityCart.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnPlusQuantityCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusQuantityCartActionPerformed(evt);
            }
        });

        txtQuantityCart.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        txtQuantityCart.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantityCart.setText("1");

        btnMinusQuantityCart.setBackground(new java.awt.Color(153, 153, 255));
        btnMinusQuantityCart.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnMinusQuantityCart.setText("-");
        btnMinusQuantityCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinusQuantityCart.setEnabled(false);
        btnMinusQuantityCart.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnMinusQuantityCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinusQuantityCartActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        jLabel1.setText("Quantity");

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblShoppingCart, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(comboModeOfPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(btnAddToCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(Total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Total1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(btnPlusQuantityCart, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantityCart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMinusQuantityCart, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCheckOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Total)
                            .addComponent(Total1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboModeOfPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(MainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnPlusQuantityCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMinusQuantityCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQuantityCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
        
        updateTotal();
        
        if ("Card Payment".equals(modeOfPayment)) {
            CardPayment cardPayment = new CardPayment(calculateTotal());
            cardPayment.setVisible(true);

            cardPayment.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    insertOrderIntoDatabase(loggedInUserID, modeOfPayment);
                }
            });
        } else {
            String message = "Do you want to proceed with checkout?";

            int confirm = JOptionPane.showConfirmDialog(this, message,
                    "Confirm Checkout", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                insertOrderIntoDatabase(loggedInUserID, modeOfPayment);
                insertCashIntoDatabase(loggedInUserID, String.valueOf(calculateTotal()));
            }
        }
    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Login_Form login = Login_Form.getInstance();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        int row = Table_Products.getSelectedRow();
        if (row != -1) {
            String productId = Table_Products.getValueAt(row, 0).toString();
            String productName = Table_Products.getValueAt(row, 1).toString();
            String priceString = Table_Products.getValueAt(row, 2).toString();

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
                quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity <= 0) {
                    throw new NumberFormatException("Quantity must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid quantity value! Please enter a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
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
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void btnPlusQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusQuantityActionPerformed
        int quantity = Integer.parseInt(txtQuantity.getText());

        int selectedRow = Table_Products.getSelectedRow();
        if (selectedRow != -1) {
            String productId = Table_Products.getValueAt(selectedRow, 0).toString();

            int currentStock = getCurrentStock(productId);

            if (quantity + 1 > currentStock) {
                JOptionPane.showMessageDialog(this, "Cannot increase quantity. Only " + currentStock + " items available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            quantity += 1;
            txtQuantity.setText(Integer.toString(quantity));
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        btnMinusQuantity.setEnabled(quantity > 1);
    }//GEN-LAST:event_btnPlusQuantityActionPerformed

    private void btnMinusQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusQuantityActionPerformed
        int quantity = Integer.parseInt(txtQuantity.getText());

        if (quantity > 1) {
            quantity -= 1;
            txtQuantity.setText(Integer.toString(quantity));
        }

        btnMinusQuantity.setEnabled(quantity > 1);
    }//GEN-LAST:event_btnMinusQuantityActionPerformed

    private void btnRemoveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveProductActionPerformed
        int selectedRow = Table_ShoppingCart.getSelectedRow(); 
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to remove from the cart.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        cartTableModel = (DefaultTableModel) Table_ShoppingCart.getModel();
        cartTableModel.removeRow(selectedRow);

        updateTotal();
    }//GEN-LAST:event_btnRemoveProductActionPerformed

    private void Table_ProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ProductsMouseClicked
        int row = Table_Products.getSelectedRow();
            if (row != -1) {
                String productId = Table_Products.getValueAt(row, 0).toString();
                updateProductDetails(productId);
            }
    }//GEN-LAST:event_Table_ProductsMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchProducts();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        searchProducts();
    }//GEN-LAST:event_lblSearchMouseClicked

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseExited
        
    }//GEN-LAST:event_txtSearchMouseExited

    private void txtSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseReleased
        
    }//GEN-LAST:event_txtSearchMouseReleased

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnPlusQuantityCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusQuantityCartActionPerformed
        int selectedRow = Table_ShoppingCart.getSelectedRow();
        int currentQuantity = 0;

        if (selectedRow != -1) {
            currentQuantity = (int) Table_ShoppingCart.getValueAt(selectedRow, 2);
            int currentStock = getCurrentStock(productId); 

            if (currentQuantity + 1 > currentStock) {
                JOptionPane.showMessageDialog(this, "Cannot increase quantity. Only " + currentStock + " items available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentQuantity++;
            txtQuantityCart.setText(String.valueOf(currentQuantity));
            Table_ShoppingCart.setValueAt(currentQuantity, selectedRow, 2);

            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product from the cart first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        btnMinusQuantityCart.setEnabled(currentQuantity > 1);
    }//GEN-LAST:event_btnPlusQuantityCartActionPerformed

    private void btnMinusQuantityCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusQuantityCartActionPerformed
        int selectedRow = Table_ShoppingCart.getSelectedRow();
        int currentQuantity = 0;

        if (selectedRow != -1) {
            currentQuantity = (int) Table_ShoppingCart.getValueAt(selectedRow, 2);

            if (currentQuantity > 1) {
                currentQuantity--;
                txtQuantityCart.setText(String.valueOf(currentQuantity));
                Table_ShoppingCart.setValueAt(currentQuantity, selectedRow, 2);
                updateTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Quantity cannot be less than 1.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product from the cart first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        btnMinusQuantityCart.setEnabled(currentQuantity > 1);
    }//GEN-LAST:event_btnMinusQuantityCartActionPerformed

    private void Table_ShoppingCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ShoppingCartMouseClicked
        int row = Table_ShoppingCart.getSelectedRow();
        if (row != -1) {
            int quantity = (int) Table_ShoppingCart.getValueAt(row, 2);
            txtQuantityCart.setText(String.valueOf(quantity)); 
            btnMinusQuantityCart.setEnabled(quantity > 1);
        } else {
            System.out.println("No row selected."); 
        }
    }//GEN-LAST:event_Table_ShoppingCartMouseClicked

    private void clearProductDetails() {
        lblProductName.setText("...");
        lblProductDesc.setText("");
        lblSupplierName.setText("...");
        lblProductImage.setIcon(null);
    }
    
    public void refreshProducts() {
        loadProducts();
        txtSearch.setText("");
        clearProductDetails();

        int selectedRow = Table_Products.getSelectedRow();
        if (selectedRow != -1) {
            String productId = Table_Products.getValueAt(selectedRow, 0).toString();
            updateProductDetails(productId);
        } else {
            lblProductName.setText("...");
            lblProductDesc.setText("");
            lblSupplierName.setText("...");
            lblProductImage.setIcon(null);
        }
    }
    
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refreshProducts();
    }//GEN-LAST:event_btnRefreshActionPerformed

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
    private javax.swing.JButton btnMinusQuantityCart;
    private javax.swing.JButton btnPlusQuantity;
    private javax.swing.JButton btnPlusQuantityCart;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveProduct;
    private javax.swing.JComboBox<String> comboModeOfPayment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea lblProductDesc;
    private javax.swing.JLabel lblProductImage;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblShoppingCart;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtQuantityCart;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
