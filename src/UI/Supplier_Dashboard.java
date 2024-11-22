package UI;

import Users.UserSession;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class Supplier_Dashboard extends javax.swing.JFrame {
    private DefaultTableModel productTableModel;

    public Supplier_Dashboard() {
        initComponents();
        loadProducts();
        addTableMouseListener();
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
    
    private String getLoggedInUserID() {
        return Login_Form.loggedInUserID;
    }
    
    private void loadProducts() {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser    = "root";
        String dbPassword = "";

        productTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Stocks"}, 0);
        Table_Products.setModel(productTableModel);

        String loggedInUserId = getLoggedInUserID(); 

        System.out.println(loggedInUserId);

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String query = "SELECT p.ProductID, p.Name, p.Price, p.Stocks FROM products p JOIN users u ON p.SupplierID = u.UserID WHERE u.UserID = ?";
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

                        productTableModel.addRow(new Object[]{productId, name, formattedPrice, stocks});
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }
    
    private String getProductImage(String productId) {
        String imagePath = "src/product_images/" + productId + ".jpg";
        return imagePath;
    }
    
    private void updateProductDetails(String productId) {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_2425";
        String dbUser  = "root";
        String dbPassword = "";

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String query = "SELECT Name, Description, SupplierID, Stocks FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String supplierId = rs.getString("SupplierID");
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(675, 670));
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
        btnBack.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
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
                                .addGap(142, 142, 142)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        this.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnRemoveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveProductActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTable Table_Products;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnRemoveProduct;
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
