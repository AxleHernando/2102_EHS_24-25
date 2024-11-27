package UI.Admin;

import Databases.DBConnection;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class User_Logs extends javax.swing.JFrame {
    private DefaultTableModel userLogsTableModel;

    public User_Logs() {
        initComponents();
        setIcon();
        loadUserLogs();
    }
    
     private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/Shopaloo-logo.png"));
        setIconImage(icon.getImage());
    }
     
     private void loadUserLogs() {
         userLogsTableModel = (DefaultTableModel) Table_UserLogs.getModel();
         userLogsTableModel.setRowCount(0);
         
         try (Connection con = DBConnection.Connect()) {
             String query = "SELECT LogsID, UserID, FullName, Role, Action, DATE_FORMAT(Date, '%m/%d/%Y') AS Date, DATE_FORMAT(Time, '%r') AS Time, Notes FROM user_logs";
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
     
     private void searchLogs() {
         userLogsTableModel = (DefaultTableModel) Table_UserLogs.getModel();
         userLogsTableModel.setRowCount(0);

         String searchText = txtSearch.getText().toLowerCase();

         try (Connection con = DBConnection.Connect()) {
             String query = "SELECT LogsID, UserID, FullName, Role, Action, DATE_FORMAT(o.Date, '%m/%d/%Y') AS Date, DATE_FORMAT(o.Time, '%r') AS Time FROM user_logs "
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
             JOptionPane.showMessageDialog(this, "Error searching products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearch))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(User_Logs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(User_Logs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(User_Logs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(User_Logs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new User_Logs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_UserLogs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
