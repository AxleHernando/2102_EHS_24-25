package Users;

import Sales.Inventory;
import Sales.Product;
import Sales.ShoppingCart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

public class Customer extends User {
    private ShoppingCart cart;

    public Customer(String userId, String username, String password) {
        super(userId, username, password);
        this.cart = new ShoppingCart();
    }

    public void viewProducts(Inventory inventory) {
        inventory.viewProducts();
    }

    public void addProductToCart(String productId, int quantity) {
        Product product = cart.getProductById(productId);
        if (product != null) {
            cart.addProduct(product, quantity);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void removeProductFromCart(String productId) {
        cart.removeProduct(productId);
        System.out.println("Product removed from cart.");
    }

    public void viewCart() {
        cart.viewCart();
    }

    public void checkout() {
        cart.checkout();
        System.out.println("Order placed successfully!");
        saveOrderToDatabase();
    }

    private void saveOrderToDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/oop_ehub_sales";
        String dbUser  = "root";
        String dbPassword = "";
        try (Connection con = DriverManager.getConnection(dbUrl, dbUser , dbPassword)) {
            String insertOrderQuery = "INSERT INTO orders (CustomerID, ProductID, Quantity) VALUES (?, ?, ?)";
            for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
                try (PreparedStatement ps = con.prepareStatement(insertOrderQuery)) {
                    ps.setString(1, getUserID());
                    ps.setString(2, entry.getKey().getProductId());
                    ps.setInt(3, entry.getValue());
                    ps.executeUpdate();
                }
            }
            cart.clearCart();
        } catch (Exception e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }
}