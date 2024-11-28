package UI.Admin;

import Databases.DBConnection;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class User_Logs extends javax.swing.JFrame {
    private DefaultTableModel userLogsTableModel;

    public User_Logs() {
        initComponents();
        setIcon();
        loadUserLogs("Date", "Descending");
    }
    
     private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }
     
    private void loadUserLogs(String sortBy, String sortOrder) {
        userLogsTableModel = (DefaultTableModel) Table_UserLogs.getModel();
        userLogsTableModel.setRowCount(0);
        
        Table_UserLogs.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Table_UserLogs.getColumnModel().getColumn(0).setPreferredWidth(40);
        Table_UserLogs.getColumnModel().getColumn(1).setPreferredWidth(40);
        Table_UserLogs.getColumnModel().getColumn(2).setPreferredWidth(168);
        Table_UserLogs.getColumnModel().getColumn(3).setPreferredWidth(75);
        Table_UserLogs.getColumnModel().getColumn(4).setPreferredWidth(75);
        Table_UserLogs.getColumnModel().getColumn(5).setPreferredWidth(100);
        Table_UserLogs.getColumnModel().getColumn(6).setPreferredWidth(100);
        Table_UserLogs.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < Table_UserLogs.getColumnModel().getColumnCount(); i++) {
            Table_UserLogs.getColumnModel().getColumn(i).setResizable(false);
        }

        if (comboSort.getItemCount() == 0) {
            comboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Date", "UID", "LID", "Role"}));
        }
        
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT LogsID, UserID, FullName, Role, Action, DATE_FORMAT(Date, '%m/%d/%Y') AS Date, DATE_FORMAT(Time, '%r') AS Time, Notes FROM user_logs";
            
            if (sortBy != null && !sortBy.isEmpty()) {
                if (sortBy.equals("Date")) {
                    query += " ORDER BY Date " + (sortOrder.equalsIgnoreCase("Recent") ? "DESC" : "ASC")
                            + ", Time " + (sortOrder.equalsIgnoreCase("Recent") ? "DESC" : "ASC");
                } else {
                    query += " ORDER BY " + getSortColumn(sortBy) + " "
                            + (sortOrder.equalsIgnoreCase("Ascending") ? "ASC" : "DESC");
                }
            }
            
            System.out.println("Executing Query: " + query);
            
            try (PreparedStatement ps = con.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String LID = rs.getString("LogsID");
                        String UID = rs.getString("UserID");
                        String fullName = rs.getString("FullName");
                        String role = rs.getString("Role");
                        String action = rs.getString("Action");
                        String date = rs.getString("Date");
                        String time = rs.getString("Time");

                        userLogsTableModel.addRow(new Object[]{LID, UID, fullName, role, action, date, time});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading user logs data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getSortColumn(String sortBy) {
        switch (sortBy) {
            case "Date":
                return "Date, Time";
            case "UID":
                return "UserID";
            case "LID":
                return "LogsID";
            case "Role":
                return "Role";
            default:
                return "LogsID";
        }
    }
     
    private void searchLogs() {
        userLogsTableModel = (DefaultTableModel) Table_UserLogs.getModel();
        userLogsTableModel.setRowCount(0);

        String searchText = txtSearch.getText().toLowerCase();

        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT LogsID, UserID, FullName, Role, Action, DATE_FORMAT(Date, '%m/%d/%Y') AS Date, DATE_FORMAT(Time, '%r') AS Time FROM user_logs "
                    + "WHERE LOWER(FullName) LIKE ? OR LOWER(Role) LIKE ? OR LOWER(Action) LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                String searchPattern = "%" + searchText + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String LID = rs.getString("LogsID");
                        String UID = rs.getString("UserID");
                        String fullName = rs.getString("FullName");
                        String role = rs.getString("Role");
                        String action = rs.getString("Action");
                        String date = rs.getString("Date");
                        String time = rs.getString("Time");

                        userLogsTableModel.addRow(new Object[]{LID, UID, fullName, role, action, date, time});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching logs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateNotes(String logsID) {
        try (Connection con = DBConnection.Connect()) {
            String query = "SELECT Notes FROM user_logs WHERE LogsID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, logsID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    txtNote.setText(rs.getString("Notes"));
                }
            }
        } catch (Exception e) {
           System.out.println("Error retrieving product details: " + e.getMessage());
       }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Table_UserLogs = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboSort = new javax.swing.JComboBox<>();
        comboSortOrder = new javax.swing.JComboBox<>();
        btnRefresh = new javax.swing.JButton();

        setResizable(false);

        Table_UserLogs.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        Table_UserLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "LID", "UID", "User", "Role", "Action", "Date", "Time"
            }
        ));
        Table_UserLogs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_UserLogsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_UserLogs);

        txtNote.setColumns(20);
        txtNote.setLineWrap(true);
        txtNote.setRows(5);
        txtNote.setWrapStyleWord(true);
        txtNote.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setViewportView(txtNote);

        jLabel1.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel1.setText("Note:");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSearch))
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearch))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        jLabel2.setText("Sort By:");

        comboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "Role", "UID", "LID" }));
        comboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortActionPerformed(evt);
            }
        });

        comboSortOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortOrderActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboSortOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefresh)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRefresh)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboSortOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(comboSort))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchLogs();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        searchLogs();
    }//GEN-LAST:event_lblSearchMouseClicked

    private void Table_UserLogsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UserLogsMouseClicked
        int row = Table_UserLogs.getSelectedRow();
        if (row != -1) {
            String logsID = Table_UserLogs.getValueAt(row, 0).toString();
            updateNotes(logsID);
        }
    }//GEN-LAST:event_Table_UserLogsMouseClicked

    private void updateSortOrderOptions(String sortBy) {
        comboSortOrder.removeAllItems();
        switch (sortBy) {
            case "Date":
                comboSortOrder.addItem("Recent");
                comboSortOrder.addItem("Old");
                break;
            case "UID":
            case "LID":
                comboSortOrder.addItem("Ascending");
                comboSortOrder.addItem("Descending");
                break;
            case "Role":
                comboSortOrder.addItem("Admin");
                comboSortOrder.addItem("Customer");
                break;
            default:
                break;
        }
        comboSortOrder.setEnabled(true);
    }
    
    private void comboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortActionPerformed
        String sortBy = (String) comboSort.getSelectedItem();
        updateSortOrderOptions(sortBy);
        loadUserLogs(sortBy, (String) comboSortOrder.getSelectedItem());
    }//GEN-LAST:event_comboSortActionPerformed

    private void comboSortOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortOrderActionPerformed
        String sortBy = (String) comboSort.getSelectedItem();
        String sortOrder = (String) comboSortOrder.getSelectedItem();
        loadUserLogs(sortBy, sortOrder);
    }//GEN-LAST:event_comboSortOrderActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadUserLogs(null, null);
    }//GEN-LAST:event_btnRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_UserLogs;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> comboSort;
    private javax.swing.JComboBox<String> comboSortOrder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
