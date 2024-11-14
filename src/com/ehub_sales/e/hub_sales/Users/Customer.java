package com.ehub_sales.e.hub_sales.Users;

import com.ehub_sales.e.hub_sales.Sales.Inventory;
import com.ehub_sales.e.hub_sales.Sales.Product;
import com.ehub_sales.e.hub_sales.Sales.ShoppingCart;

public class Customer extends User {
    private ShoppingCart cart;

    public Customer(String userId, String username, String password) {
        super(userId, username, password);
        this.cart = new ShoppingCart();
    }

    // Method to view products
    public void viewProducts(Inventory inventory) {
        inventory.viewProducts();
    }

    // Method to add a product to the shopping cart
    public void addProductToCart(Inventory inventory, String productId, int quantity) {
        Product product = inventory.getProductById(productId);
        if (product != null) {
            cart.addProduct(product, quantity);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // Method to remove a product from the shopping cart
    public void removeProductFromCart(String productId) {
        cart.removeProduct(productId);
        System.out.println("Product removed from cart.");
    }

    // Method to view the shopping cart
    public void viewCart() {
        cart.viewCart();
    }

    // Method to checkout
    public void checkout() {
        cart.checkout();
        System.out.println("Order placed successfully!");
    }
}
