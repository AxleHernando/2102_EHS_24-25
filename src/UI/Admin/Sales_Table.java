package UI.Admin;

import Databases.DBConnection;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Sales_Table extends javax.swing.JFrame {

    private DefaultTableModel salesTableModel;

    public Sales_Table() {
        initComponents();
        loadSalesData(null, null, null);
        setIcon();
    }
    
    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }

    private void loadSalesData(String categoryFilter, String sortBy, String sortOrder) {
        salesTableModel = (DefaultTableModel) Sales_Table.getModel();
        salesTableModel.setRowCount(0);
        double totalSales = 0.0;
        
        Sales_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Sales_Table.getColumnModel().getColumn(0).setPreferredWidth(40);
        Sales_Table.getColumnModel().getColumn(1).setPreferredWidth(140);
        Sales_Table.getColumnModel().getColumn(2).setPreferredWidth(32);
        Sales_Table.getColumnModel().getColumn(3).setPreferredWidth(84);
        Sales_Table.getColumnModel().getColumn(4).setPreferredWidth(150);
        Sales_Table.getColumnModel().getColumn(5).setPreferredWidth(100);
        Sales_Table.getColumnModel().getColumn(6).setPreferredWidth(168);
        Sales_Table.getColumnModel().getColumn(7).setPreferredWidth(75);
        Sales_Table.getColumnModel().getColumn(8).setPreferredWidth(75);
        Sales_Table.getTableHeader().setReorderingAllowed(false);
        
        for (int i = 0; i < Sales_Table.getColumnModel().getColumnCount(); i++) {
            Sales_Table.getColumnModel().getColumn(i).setResizable(false);
        }

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT o.OrderID, p.Name, o.Quantity, o.Price, DATE_FORMAT(o.Date, '%m/%d/%Y') AS Date, DATE_FORMAT(o.Time, '%r') AS Time, u.FullName, p.Category, o.ModeOfPayment "
                    + "FROM orders o "
                    + "JOIN products p ON o.ProductID = p.ProductID "
                    + "JOIN users u ON o.UserID = u.UserID";
            
            boolean hasWhereClause = false;
            if (categoryFilter != null && !categoryFilter.isEmpty()) {
                query += " WHERE p.Category = ?";
                hasWhereClause = true;
            }
            
            if (sortBy != null && !sortBy.isEmpty()) {
                if (hasWhereClause) {
                    query += " AND ";
                } else if (sortBy.equals("Date")) {
                    query += " ORDER BY Date " + (sortOrder.equalsIgnoreCase("Recent") ? "DESC" : "ASC")
                            + ", Time " + (sortOrder.equalsIgnoreCase("Recent") ? "DESC" : "ASC");
                } else {
                    query += " ORDER BY " + getSortColumn(sortBy) + " "
                            + (sortOrder.equalsIgnoreCase("Ascending") || sortOrder.equalsIgnoreCase("Lowest") ? "ASC" : "DESC");
                }
            }

            if (!hasWhereClause && (sortBy == null || sortBy.isEmpty())) {
                query += " ORDER BY o.OrderID ASC";
            } else if (sortBy != null && sortBy.equalsIgnoreCase("OrderID")) {
                query += " ORDER BY o.OrderID "
                        + (sortOrder.equalsIgnoreCase("Ascending") || sortOrder.equalsIgnoreCase("Lowest") ? "ASC" : "DESC");
            }
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                if (categoryFilter != null && !categoryFilter.isEmpty()) {
                    ps.setString(1, categoryFilter);
                }
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String orderId = rs.getString("OrderID");
                        String productName = rs.getString("Name");
                        int quantity = rs.getInt("Quantity");
                        double price = rs.getDouble("Price");
                        String saleDate = rs.getString("Date");
                        String saleTime = rs.getString("Time");
                        String fullName = rs.getString("FullName");
                        String category = rs.getString("Category");
                        String MOP = rs.getString("ModeOfPayment");

                        totalSales += price;

                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                        String formattedPrice = formatter.format(price);

                        salesTableModel.addRow(new Object[]{orderId, productName, quantity, formattedPrice, saleDate, saleTime, fullName, category, MOP});
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading sales data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading sales data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        lblTotalSales.setText(formatter.format(totalSales));
    }
    
    private String getSortColumn(String sortBy) {
        switch (sortBy) {
            case "Date":
                return "o.Date, o.Time";
            case "Price":
                return "o.Price";
            case "ID":
                return "o.OrderID";
            default:
                return "o.OrderID";
        }
    }
    
    private void searchProducts() {
        String searchText = txtSearch.getText().toLowerCase();
        salesTableModel.setRowCount(0);
        double totalSales = 0.0;

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT o.OrderID, p.Name, o.Quantity, o.Price, DATE_FORMAT(o.Date, '%m/%d/%Y') AS Date, DATE_FORMAT(o.Time, '%r') AS Time, u.FullName, p.Category, o.ModeOfPayment "
                    + "FROM orders o "
                    + "JOIN products p ON o.ProductID = p.ProductID "
                    + "JOIN users u ON o.UserID = u.UserID "
                    + "WHERE LOWER(p.Name) LIKE ? OR LOWER(u.FullName) LIKE ? OR LOWER(o.Category) LIKE ? OR (o.Date) LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                String searchPattern = "%" + searchText + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String orderId = rs.getString("OrderID");
                        String productName = rs.getString("Name");
                        int quantity = rs.getInt("Quantity");
                        double price = rs.getDouble("Price");
                        String saleDate = rs.getString("Date");
                        String saleTime = rs.getString("Time");
                        String fullName = rs.getString("FullName");
                        String category = rs.getString("Category");
                        String MOP = rs.getString("ModeOfPayment");

                        totalSales += price;

                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
                        String formattedPrice = formatter.format(price);

                        salesTableModel.addRow(new Object[]{orderId, productName, quantity, formattedPrice, saleDate, saleTime, fullName, category, MOP});
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error searching products: " + e.getMessage());
        }
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        lblTotalSales.setText(formatter.format(totalSales));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        Sales_Table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblTotalSales = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        List_Category2 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        comboSort = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        comboSortOrder = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sales");
        setResizable(false);

        Sales_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PID", "Product Name", "Qty", "Price", "Date", "Time", "Customer", "Category", "Payment"
            }
        ));
        jScrollPane2.setViewportView(Sales_Table);
        if (Sales_Table.getColumnModel().getColumnCount() > 0) {
            Sales_Table.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTotalSales.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        lblTotalSales.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalSales, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOTAL SALES");

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

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        List_Category2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        List_Category2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        List_Category2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Gadgets", "Hygiene", "Kitchen", "Clothes", "Appliances", "Gaming" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        List_Category2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                List_Category2MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(List_Category2);

        jLabel4.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        jLabel4.setText("Categories");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6)
                .addContainerGap())
        );

        comboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Price", "ID" }));
        comboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel2.setText("Sort By:");

        btnRefresh.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        btnRefresh.setText("REFRESH");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        comboSortOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboSortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRefresh)
                                .addGap(34, 34, 34)))
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboSortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSearch)
                                    .addComponent(lblSearch))
                                .addComponent(btnRefresh)))
                        .addGap(11, 11, 11))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseExited

    }//GEN-LAST:event_txtSearchMouseExited

    private void txtSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseReleased

    }//GEN-LAST:event_txtSearchMouseReleased

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchProducts();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        searchProducts();
    }//GEN-LAST:event_lblSearchMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        
    }//GEN-LAST:event_txtSearchActionPerformed

    private void List_Category2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_List_Category2MouseClicked
        String selectedCategory = List_Category2.getSelectedValue();
        if (selectedCategory != null) {
            loadSalesData(selectedCategory, null, null);
        }
    }//GEN-LAST:event_List_Category2MouseClicked

    private void comboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortActionPerformed
        String sortBy = (String) comboSort.getSelectedItem();
        updateSortOrderOptions(sortBy);
        loadSalesData(null, sortBy, (String) comboSortOrder.getSelectedItem());
    }//GEN-LAST:event_comboSortActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadSalesData(null, null, null);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void comboSortOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortOrderActionPerformed
        String sortBy = (String) comboSort.getSelectedItem();
        String sortOrder = (String) comboSortOrder.getSelectedItem();
        loadSalesData(null, sortBy, sortOrder);
    }//GEN-LAST:event_comboSortOrderActionPerformed

    private void updateSortOrderOptions(String sortBy) {
        comboSortOrder.removeAllItems();
        switch (sortBy) {
            case "ID":
                comboSortOrder.addItem("Ascending");
                comboSortOrder.addItem("Descending");
                break;
            case "Price":
                comboSortOrder.addItem("Lowest");
                comboSortOrder.addItem("Highest");
                break;
            case "Date":
                comboSortOrder.addItem("Recent");
                comboSortOrder.addItem("Old");
                break;
            default:
                comboSortOrder.addItem("Ascending");
                break;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> List_Category2;
    private javax.swing.JTable Sales_Table;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> comboSort;
    private javax.swing.JComboBox<String> comboSortOrder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTotalSales;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
