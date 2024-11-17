package com.ehub_sales.e.hub_sales.Users;

import com.ehub_sales.e.hub_sales.Sales.Inventory;
import com.ehub_sales.e.hub_sales.Sales.Product;

public class Supplier extends User {
    public Supplier(String userId, String username, String password) {
        super(userId, username, password);
    }

    // Method to add a product
    public void addProduct(Inventory inventory, Product product) {
        inventory.addProduct(product);
    }

    // Method to delete a product
    public void deleteProduct(Inventory inventory, String productId) {
        inventory.deleteProduct(productId);
    }

    // Method to update product details
    public void updateProduct(Inventory inventory, String productId, String name, String description, double price, int quantity) {
        inventory.updateProduct(productId, name, description, price, quantity);
    }

    // Method to view all products
    public void viewProducts(Inventory inventory) {
        inventory.viewProducts();
    }
}