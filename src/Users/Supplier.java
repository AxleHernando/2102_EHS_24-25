package Users;

import Sales.Inventory;
import Sales.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Supplier extends User {
    public Supplier(String userId, String username, String password) {
        super(userId, username, password);
    }

    public void viewProducts(Inventory inventory) {
        inventory.viewProducts();
    }

    public void addProduct(Inventory inventory, Product product) {
        inventory.addProduct(product);
        saveProductToDatabase(product);
    }

    public void removeProduct(Inventory inventory, String productId) {
        inventory.deleteProduct(productId);
        deleteProductFromDatabase(productId);
    }

    public void editProduct(Inventory inventory, String productId, String name, String description, double price, int quantity) {
        inventory.updateProduct(productId, name, description, price, quantity);
        updateProductInDatabase(productId, name, description, price, quantity);
    }

    public void viewCustomerOrders(String supplierId) {
        // Logic to view customer orders for this supplier
    }

    private void saveProductToDatabase(Product product) {
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser  = "root";
        String dbPassword = "";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String insertProductQuery = "INSERT INTO products (ProductID, Name, Description, Price, Quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertProductQuery)) {
                ps.setString(1, product.getProductId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                ps.setInt(5, product.getQuantity());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error saving product: " + e.getMessage());
        }
    }

    private void updateProductInDatabase(String productId, String name, String description, double price, int quantity) {
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser   = "root";
        String dbPassword = "";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser  , dbPassword)) {
            String updateProductQuery = "UPDATE products SET Name=?, Description=?, Price=?, Quantity=? WHERE ProductID=?";
            try (PreparedStatement ps = con.prepareStatement(updateProductQuery)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setDouble(3, price);
                ps.setInt(4, quantity);
                ps.setString(5, productId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    private void deleteProductFromDatabase(String productId) {
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser   = "root";
        String dbPassword = "";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser  , dbPassword)) {
            String deleteProductQuery = "DELETE FROM products WHERE ProductID=?";
            try (PreparedStatement ps = con.prepareStatement(deleteProductQuery)) {
                ps.setString(1, productId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }
}