package Sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalesReport {
    public void generateReport() {
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser   = "root";
        String dbPassword = "";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser  , dbPassword)) {
            String query = "SELECT * FROM orders";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String orderId = rs.getString("OrderID");
                    String customerId = rs.getString("CustomerID");
                    String productId = rs.getString("ProductID");
                    int quantity = rs.getInt("Quantity");
                    System.out.println("Order ID: " + orderId + ", Customer ID: " + customerId + ", Product ID: " + productId + ", Quantity: " + quantity);
                }
            }
        } catch (Exception e) {
            System.out.println("Error generating sales report: " + e.getMessage());
        }
    }
}