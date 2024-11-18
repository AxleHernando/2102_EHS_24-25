package Sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        products = new ArrayList<>();
        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser  = "root";
        String dbPassword = "";

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String query = "SELECT * FROM products";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String productId = rs.getString("ProductID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    double price = rs.getDouble("Price");
                    int quantity = rs.getInt("Quantity");

                    products.add(new Product(productId, name, description, price, quantity));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public List<Product> getProducts() {
        return products; // Method to retrieve the list of products
    }

    public void viewProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String productId, String name, String description, double price, int quantity) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setQuantity(quantity);
                break;
            }
        }
    }

    public void deleteProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
    }
}