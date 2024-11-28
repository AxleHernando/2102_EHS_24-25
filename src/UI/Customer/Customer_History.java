package UI.Customer;

import Databases.DBConnection;
import Objects.UserSession;
import java.awt.event.KeyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class Customer_History extends javax.swing.JFrame {
    private DefaultTableModel sortedOrders;
    
    public Customer_History() {
        initComponents();
        loadHistory("Date", "Recent");
    }
    
    private void loadHistory(String sortBy, String sortOrder){
        sortedOrders = new DefaultTableModel(new String[]{"OID", "Name", "Products", "Qty", "Price", "Payment", "Date", "Time"}, 0);
        sortedOrders_Table.setModel(sortedOrders);
        sortedOrders_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        sortedOrders_Table.getColumnModel().getColumn(0).setPreferredWidth(50);
        sortedOrders_Table.getColumnModel().getColumn(1).setPreferredWidth(168);
        sortedOrders_Table.getColumnModel().getColumn(2).setPreferredWidth(140);
        sortedOrders_Table.getColumnModel().getColumn(3).setPreferredWidth(50);
        sortedOrders_Table.getColumnModel().getColumn(5).setPreferredWidth(75);
        sortedOrders_Table.getColumnModel().getColumn(7).setPreferredWidth(85);
        sortedOrders_Table.getTableHeader().setReorderingAllowed(false);
        
        for (int i = 0; i < sortedOrders_Table.getColumnModel().getColumnCount(); i++) {
            sortedOrders_Table.getColumnModel().getColumn(i).setResizable(false);
        }

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT OrderID, Name, Products, Qty, Price, Payment, DATE_FORMAT(Date, '%m/%d/%Y') AS Date, DATE_FORMAT(Time, '%r') AS Time FROM orderhistory WHERE Name = ?";
            String queryUser = "SELECT FullName FROM users WHERE UserID = ?";
            String userID = UserSession.getCurrentUserID();
            
            if (sortBy != null && !sortBy.isEmpty()) {
                query += " ORDER BY " + getSortColumn(sortBy) + " "
                        + (sortOrder != null && (sortOrder.equals("Ascending") || sortOrder.equals("Old") || sortOrder.equals("Admin")) ? "ASC" : "DESC");
            }
            
            try (PreparedStatement psUser = con.prepareStatement(queryUser)) {
                psUser.setString(1, userID);
                try (ResultSet rsUser = psUser.executeQuery()) {
                    if (rsUser.next()) {
                        String fullName = rsUser.getString("FullName");
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setString(1, fullName);
                            try (ResultSet rs = ps.executeQuery()) {
                                while (rs.next()) {
                                    String OID = rs.getString("OrderID");
                                    String name = rs.getString("Name");
                                    String product = rs.getString("Products");
                                    int qty = rs.getInt("Qty");
                                    double price = rs.getDouble("Price");
                                    String payment = rs.getString("Payment");
                                    String date = rs.getString("Date");
                                    String time = rs.getString("Time");

                                    sortedOrders.addRow(new Object[]{OID, name, product, qty, price, payment, date, time});
                                }
                            }
                        }
                    } else {
                        System.out.println("No user found with ID " + userID);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading order history: " + e.getMessage());
        }
    }
    
    private String getSortColumn(String sortBy) {
        switch (sortBy) {
            case "Date":
                return "Date, Time";
            case "OID":
                return "OrderID";
            case "Price":
                return "Price";
            case "Qty":
                return "Qty";
            default:
                return "OrderID";
        }
    }
    
    private void searchHistory() {
        sortedOrders = (DefaultTableModel) sortedOrders_Table.getModel();
        sortedOrders.setRowCount(0);

        String searchText = txtSearch.getText().toLowerCase();

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT OrderID, Name, Products, Qty, Price, Payment, DATE_FORMAT(Date, '%m/%d/%Y') AS Date, DATE_FORMAT(Time, '%r') AS Time FROM orderhistory "
                    + "WHERE LOWER(Products) LIKE ? OR LOWER(Payment) LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                String searchPattern = "%" + searchText + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String OID = rs.getString("OrderID");
                        String name = rs.getString("Name");
                        String products = rs.getString("Products");
                        String qty = rs.getString("Qty");
                        String price = rs.getString("Price");
                        String payment = rs.getString("Payment");
                        String date = rs.getString("Date");
                        String time = rs.getString("Time");

                        sortedOrders.addRow(new Object[]{OID, name, products, qty, price, payment, date, time});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching logs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        sortedOrders_Table = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        comboSortOrder = new javax.swing.JComboBox<>();
        comboSort = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();

        setResizable(false);

        sortedOrders_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "OID", "Name", "Product", "Qty", "Price", "Payment", "Date", "Time"
            }
        ));
        jScrollPane1.setViewportView(sortedOrders_Table);

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

        comboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Qty", "Price", "OID" }));
        comboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel2.setText("Sort By:");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboSortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSearch)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSearch))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboSortOrder)
                            .addComponent(comboSort, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnRefresh)
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadHistory(null, null);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void updateSortOrderOptions(String sortBy) {
        comboSortOrder.removeAllItems();
        switch (sortBy) {
            case "Date":
                comboSortOrder.addItem("Recent");
                comboSortOrder.addItem("Old");
                break;
            case "OID":
                comboSortOrder.addItem("Ascending");
                comboSortOrder.addItem("Descending");
                break;
            case "Qty":
                comboSortOrder.addItem("Lowest");
                comboSortOrder.addItem("Highest");
                break;
            case "Price":
                comboSortOrder.addItem("Admin");
                comboSortOrder.addItem("Customer");
                break;
            default:
                break;
        }
        comboSortOrder.setEnabled(true);
    }
    
    private void comboSortOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortOrderActionPerformed
        String sortBy = (String) comboSort.getSelectedItem();
        String sortOrder = (String) comboSortOrder.getSelectedItem();
        loadHistory(sortBy, sortOrder);
    }//GEN-LAST:event_comboSortOrderActionPerformed

    private void comboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortActionPerformed
        String sortBy = (String) comboSort.getSelectedItem();
        updateSortOrderOptions(sortBy);
        String sortOrder = (String) comboSortOrder.getSelectedItem();
        loadHistory(sortBy, sortOrder);
    }//GEN-LAST:event_comboSortActionPerformed

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
            searchHistory();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        searchHistory();
    }//GEN-LAST:event_lblSearchMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> comboSort;
    private javax.swing.JComboBox<String> comboSortOrder;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTable sortedOrders_Table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
