package UI.Admin;

import Databases.DBConnection;
import Objects.UserSession;
import UI.Login_Form;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Admin_Dashboard extends javax.swing.JFrame {
    private DefaultTableModel productTableModel;

    public Admin_Dashboard() {
        initComponents();
        loadProducts(null);
        setIcon();
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }
    
    private void loadProducts(String categoryFilter) {
        DefaultTableModel model = (DefaultTableModel) Table_Products.getModel();
        model.setRowCount(0);
        
        Table_Products.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Table_Products.getColumnModel().getColumn(0).setPreferredWidth(40);
        Table_Products.getColumnModel().getColumn(1).setPreferredWidth(140);
        Table_Products.getColumnModel().getColumn(2).setPreferredWidth(84);
        Table_Products.getColumnModel().getColumn(3).setPreferredWidth(50);
        Table_Products.getColumnModel().getColumn(4).setPreferredWidth(75);
        Table_Products.getColumnModel().getColumn(5).setPreferredWidth(168);
        Table_Products.getTableHeader().setReorderingAllowed(false);
        
        for (int i = 0; i < Table_Products.getColumnModel().getColumnCount(); i++) {
            Table_Products.getColumnModel().getColumn(i).setResizable(false);
        }
        
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT ProductID, Name, Price, Stocks, Category, SupplierName FROM products";
            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                query += " WHERE Category = ?";
            }
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                if (categoryFilter != null && !categoryFilter.isEmpty()) {
                    ps.setString(1, categoryFilter);
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String productId = rs.getString("ProductID");
                        String name = rs.getString("Name");
                        double price = rs.getDouble("Price");
                        int stocks = rs.getInt("Stocks");
                        String category = rs.getString("Category");
                        String supplier = rs.getString("SupplierName");

                        if (name == null || name.isEmpty()) {
                            name = "N/A";
                        }

                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                        String formattedPrice = formatter.format(price);

                        model.addRow(new Object[]{productId, name, formattedPrice, stocks, category, supplier});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }
    
    private void searchProducts() {
        productTableModel = (DefaultTableModel) Table_Products.getModel();
        productTableModel.setRowCount(0);

        String searchText = txtSearch.getText().toLowerCase();

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT ProductID, Name, Price, Stocks, Category, SupplierName FROM products "
                    + "WHERE LOWER(Name) LIKE ? OR LOWER(Category) LIKE ? OR LOWER(SupplierName) LIKE ?";
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                String searchPattern = "%" + searchText + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String productId = rs.getString("ProductID");
                        String name = rs.getString("Name");
                        double price = rs.getDouble("Price");
                        int stocks = rs.getInt("Stocks");
                        String category = rs.getString("Category");
                        String supplier = rs.getString("SupplierName");

                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                        String formattedPrice = formatter.format(price);

                        productTableModel.addRow(new Object[]{productId, name, formattedPrice, stocks, category, supplier});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearProductDetails() {
        lblProductName.setText("...");
        lblProductDesc.setText("");
        lblStocks.setText("Stocks: ...");
        lblCategory.setText("Category: ...");
        lblSupplier.setText("Supplier: ...");
        lblProductImage.setIcon(null);
    }
    
    public void refreshProducts() {
        loadProducts(null);
        txtSearch.setText("Search");
        clearProductDetails();
        
        int selectedRow = Table_Products.getSelectedRow();
        if (selectedRow != -1) {
            String productId = Table_Products.getValueAt(selectedRow, 0).toString();
            updateProductDetails(productId);
        } else {
            lblProductName.setText("...");
            lblProductDesc.setText("");
            lblStocks.setText("Stocks: ...");
            lblCategory.setText("Category: ...");
            lblProductImage.setIcon(null);
        }
    }

    private BufferedImage loadProductImage(String productId) {
        String imagePath = getProductImage(productId);
        try {
            return ImageIO.read(new File(imagePath)); 
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            return null; 
        }
    }
    
    private String getProductImage(String productId) {
        return "src/product_images/" + productId + ".jpg"; 
    }
    
    private void updateProductDetails(String productId) {
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT Name, Description, Stocks, Category, SupplierName FROM products WHERE ProductID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String stocks = rs.getString("Stocks");
                    String category = rs.getString("Category");
                    String supplier = rs.getString("SupplierName");

                    lblProductName.setText(name);
                    lblProductDesc.setText(description); 
                    lblStocks.setText("Stocks: " + stocks);
                    lblCategory.setText("Category: " + category);
                    lblSupplier.setText("Supplier: " + supplier);
                    
                    BufferedImage img = loadProductImage(productId);
                    if (img != null) {
                        lblProductImage.setIcon(new ImageIcon(img));
                    } else {
                        lblProductImage.setIcon(null);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving product details: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Products = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblProductImage = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblProductDesc = new javax.swing.JTextArea();
        lblStocks = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblSupplier = new javax.swing.JLabel();
        btnEditProduct = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        btnRemoveProduct = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnViewSales = new javax.swing.JButton();
        lblWelcome1 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        List_Category = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        btnUserLogs = new javax.swing.JButton();

        lblWelcome.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin");
        setResizable(false);

        MainPanel.setBackground(new java.awt.Color(248, 248, 248));

        Table_Products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "PID", "Name", "Price", "Stock", "Category", "Supplier"
            }
        ));
        Table_Products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Products);

        lblProductImage.setBackground(new java.awt.Color(255, 255, 255));
        lblProductImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblProductName.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        lblProductName.setText("...");

        lblProductDesc.setColumns(20);
        lblProductDesc.setLineWrap(true);
        lblProductDesc.setRows(5);
        lblProductDesc.setToolTipText("");
        lblProductDesc.setWrapStyleWord(true);
        lblProductDesc.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setViewportView(lblProductDesc);

        lblStocks.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblStocks.setText("...");

        lblCategory.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblCategory.setText("...");

        lblSupplier.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        lblSupplier.setText("...");

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategory)
                    .addComponent(lblSupplier))
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
                        .addComponent(lblSupplier)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStocks, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCategory)
                        .addGap(13, 13, 13))
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

        lblWelcome1.setFont(new java.awt.Font("Helvetica", 1, 26)); // NOI18N
        lblWelcome1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADMIN\\Downloads\\SHOP-A-LOO (1) (2).png")); // NOI18N

        btnRefresh.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        btnRefresh.setText("REFRESH");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

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

        List_Category.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        List_Category.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        List_Category.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Gadgets", "Hygiene", "Kitchen", "Clothes", "Appliances", "Gaming" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        List_Category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                List_CategoryMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(List_Category);

        jLabel2.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        jLabel2.setText("Categories");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4)
                .addContainerGap())
        );

        btnUserLogs.setBackground(new java.awt.Color(153, 153, 255));
        btnUserLogs.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnUserLogs.setText("User Logs");
        btnUserLogs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserLogs.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnUserLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserLogsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(lblWelcome1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSearch)
                        .addGap(20, 20, 20)
                        .addComponent(btnRefresh)
                        .addGap(18, 18, 18)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUserLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblWelcome1)
                            .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRefresh))))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSearch))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to Log Out?", "Log Out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            String query = "SELECT * FROM users WHERE UserID = ?";
            String queryLogs = "INSERT INTO user_logs (UserID, FullName, Role, Action, Date, Time, Notes) VALUES (?, ?, ?, ?, "
                    + "STR_TO_DATE(DATE_FORMAT(CURDATE(), '%m/%d/%Y'), '%m/%d/%Y'), "
                    + "DATE_FORMAT(NOW(), '%H:%i:%s'), ?)";
            String userID = UserSession.getCurrentUserID();
            try (Connection con = DBConnection.Connect(); PreparedStatement ps = con.prepareStatement(query); PreparedStatement psLogs = con.prepareStatement(queryLogs)) {
                ps.setString(1, userID);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String fullName = rs.getString("FullName");
                    String role = rs.getString("Role");

                    psLogs.setString(1, userID);
                    psLogs.setString(2, fullName);
                    psLogs.setString(3, role);
                    psLogs.setString(4, "Logged Out");
                    psLogs.setString(5, fullName + " has Logged Out.");
                    psLogs.executeUpdate();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            }

            Login_Form login = Login_Form.getInstance();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        int selectedRow = Table_Products.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
            return;
        }

        String selectedProductID = Table_Products.getValueAt(selectedRow, 0).toString();
        Edit_Products editForm = new Edit_Products(this, selectedProductID);
        editForm.setVisible(true);
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        new Add_Products(this).setVisible(true);
    }//GEN-LAST:event_btnAddProductActionPerformed
    
    private void btnRemoveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveProductActionPerformed
        int selectedRow = Table_Products.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        productTableModel = (DefaultTableModel) Table_Products.getModel();
        String productId = Table_Products.getValueAt(selectedRow, 0).toString();
        String supplier = Table_Products.getValueAt(selectedRow, 5).toString();
        String product = lblProductName.getText();
        String desc = lblProductDesc.getText();
        String price = Table_Products.getValueAt(selectedRow, 2).toString();
        String stocks = Table_Products.getValueAt(selectedRow, 3).toString();
        String category = Table_Products.getValueAt(selectedRow, 4).toString();
                
        String message = "Supplier: " + supplier 
                + "Product: " + product + "\n"
                + "Description: " + desc + "\n"
                + "Price: " + price + "\n"
                + "Stocks: " + stocks + "\n"
                + "Category: " + category + "\n"
                + "Do you want to proceed?";

        int confirm = JOptionPane.showConfirmDialog(this, message,
                "Confirm Delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DBConnection.Connect()) {
                String removeQuery = "DELETE FROM products WHERE ProductID = ?";
                try (PreparedStatement ps = con.prepareStatement(removeQuery)) {
                    ps.setString(1, productId);
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
                                psLogs.setString(4, "Remove Product");
                                psLogs.setString(5, fullName + " Removed a Product:\n\n"
                                        + "Supplier Name: " + supplier + "\n"
                                        + "Product: " + product + "\n"
                                        + "Desciption: " + desc + "\n"
                                        + "Price: " + price + "\n"
                                        + "Stocks: " + stocks + "\n"
                                        + "Category: " + category);
                                psLogs.executeUpdate();
                            }
                        }
                    }

                    if (rowsAffected > 0) {
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
                        productTableModel.removeRow(selectedRow);
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
        Sales_Table salesTableFrame = new Sales_Table();
        salesTableFrame.setVisible(true);
    }//GEN-LAST:event_btnViewSalesActionPerformed

    private void Table_ProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ProductsMouseClicked
        int row = Table_Products.getSelectedRow();
        if (row != -1) {
            String productId = Table_Products.getValueAt(row, 0).toString();
            updateProductDetails(productId);
        }
    }//GEN-LAST:event_Table_ProductsMouseClicked

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refreshProducts();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseExited

    }//GEN-LAST:event_txtSearchMouseExited

    private void txtSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseReleased

    }//GEN-LAST:event_txtSearchMouseReleased

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchProducts();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        searchProducts();
    }//GEN-LAST:event_lblSearchMouseClicked

    private void List_CategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_List_CategoryMouseClicked
        String selectedCategory = List_Category.getSelectedValue();
        if (selectedCategory != null) {
            loadProducts(selectedCategory);
        }
    }//GEN-LAST:event_List_CategoryMouseClicked

    private void btnUserLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserLogsActionPerformed
        User_Logs userLogs = new User_Logs();
        userLogs.setVisible(true);
    }//GEN-LAST:event_btnUserLogsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> List_Category;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTable Table_Products;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveProduct;
    private javax.swing.JButton btnUserLogs;
    private javax.swing.JButton btnViewSales;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JTextArea lblProductDesc;
    private javax.swing.JLabel lblProductImage;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblStocks;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel lblWelcome1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
