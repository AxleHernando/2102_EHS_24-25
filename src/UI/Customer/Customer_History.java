package UI.Customer;

import Databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class Customer_History extends javax.swing.JFrame {
    private DefaultTableModel sortedOrders;
    /**
     * Creates new form Customer_History
     */
    public Customer_History() {
        initComponents();
        loadhistory();
    }
    
    private void loadhistory(){
        sortedOrders = new DefaultTableModel(new String[]{"ID", "Name", "Products", "Qty", "Price", "Payment", "Date", "Time"}, 0);
        sortedOrders_Table.setModel(sortedOrders);
        sortedOrders_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        sortedOrders_Table.getColumnModel().getColumn(0).setPreferredWidth(50);
        sortedOrders_Table.getColumnModel().getColumn(1).setPreferredWidth(140);
        sortedOrders_Table.getColumnModel().getColumn(2).setPreferredWidth(84);
        sortedOrders_Table.getColumnModel().getColumn(3).setPreferredWidth(160);
        sortedOrders_Table.getTableHeader().setReorderingAllowed(false);
        
        for (int i = 0; i < sortedOrders_Table.getColumnModel().getColumnCount(); i++) {
            sortedOrders_Table.getColumnModel().getColumn(i).setResizable(false);
    }

    try (Connection con = DBConnection.Connect()) {
        // Define the SQL query to fetch order history
        String query = "SELECT OrderID, Name, Products, Qty, Price, Payment, Date, Time FROM orderhistory";

        // Create a Statement or PreparedStatement to execute the query
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            // Process the result set and add rows to the table model
            while (rs.next()) {
                String OID = rs.getString("OrderID");
                String name = rs.getString("Name");
                String product = rs.getString("Products");
                int qty = rs.getInt("Qty");
                double price = rs.getDouble("Price");
                String payment = rs.getString("Payment");
                String date = rs.getString("Date");
                String time = rs.getString("Time");

                // Add a row to the table model
                sortedOrders.addRow(new Object[]{OID, name, product, qty, price, payment, date, time});
            }
        }
    } catch (Exception e) {
        System.out.println("Error loading order history: " + e.getMessage());
    }

    }
       
    private void search() throws SQLException{
        String searchText = Search.getText().trim();
        
        sortedOrders.setRowCount(0);
        
        if (searchText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a name or product to search.", "Search Error", JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT OrderID, Name, Products, Qty, Price, Payment, Date, Time " +
                           "FROM orderhistory " +
                           "WHERE Name LIKE ? OR Products LIKE ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, "%" + searchText + "%");
                ps.setString(2, "%" + searchText + "%");
                
                try (ResultSet rs = ps.executeQuery()) {
                // Populate the table with the filtered results
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during search: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
    }
        if (sortedOrders.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No records found for '" + searchText + "'.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }
    }
       
    private void refresh(){
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        sortedOrders_Table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Date = new javax.swing.JComboBox<>();
        Search = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel1.setText("Sort by:");

        Date.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));
        Date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DateActionPerformed(evt);
            }
        });

        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Search.png"))); // NOI18N
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Helvetica", 1, 12)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        btnRefresh.setText("REFRESH");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSearch)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Search))))
                        .addContainerGap(155, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnRefresh)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DateActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        
    }//GEN-LAST:event_lblSearchMouseClicked

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Date;
    private javax.swing.JTextField Search;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTable sortedOrders_Table;
    // End of variables declaration//GEN-END:variables
}
