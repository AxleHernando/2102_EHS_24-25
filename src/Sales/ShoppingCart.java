package Sales;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> cartItems;

    public ShoppingCart() {
        cartItems = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
    }

    public void removeProduct(String productId) {
        cartItems.keySet().removeIf(product -> product.getProductId().equals(productId));
    }

    public void viewCart() {
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            System.out.println(entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public void checkout() {
        // Logic for checkout
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public Product getProductById(String productId) {
        for (Product product : cartItems.keySet()) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}