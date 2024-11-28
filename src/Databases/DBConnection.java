package Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
    public static Connection Connect() {
        String dbUrl = "jdbc:mysql://localhost:3306/2102_ehs_24-25";
        String dbUser  = "root";
        String dbPassword = "";
        
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Conncecting to the Database: " + e.getMessage());
            return null;
        }
    }
}
