package com.ehub_sales.e.hub_sales.Sales;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> cartItems;

    public ShoppingCart() {
        setCartItems(cartItems = new HashMap<>());
    }

    public void setCartItems(Map<Product, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public void addProduct(Product product, int quantity) {
        getCartItems().put(product, getCartItems().getOrDefault(product, 0) + quantity);
        System.out.println("Product added to cart.");
    }

    public void removeProduct(String productId) {
        Product productToRemove = null;

        // Find the product by productId
        for (Product product : cartItems.keySet()) {
            if (product.getProductId().equals(productId)) {
                productToRemove = product;
                break;
            }
        }

        // Remove product if it exists in the cart
        if (productToRemove != null) {
            cartItems.remove(productToRemove);
            System.out.println("Product removed from cart.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    public void viewCart() {
        System.out.println("Shopping Cart:");
        for (Map.Entry<Product, Integer> entry : getCartItems().entrySet()) {
            System.out.println("Product: " + entry.getKey().getProductName() + ", Quantity: " + entry.getValue());
        }
    }

    public void checkout() {
        System.out.println("Checkout successful!");
        getCartItems().clear();
    }
}
