package com.ehub_sales.e.hub_sales.Sales;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        setProducts(products = new ArrayList<>());
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        getProducts().add(product);
        System.out.println("Product added successfully!");
    }

    public void viewProducts() {
        for (Product product : getProducts()) {
            System.out.println("ID: " + product.getProductId() + ", Name: " + product.getProductName());
        }
    }

    public Product getProductById(String productId) {
        for (Product product : getProducts()) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        System.out.println("Product not found.");
        return null;
    }

    public void updateProduct(String productId, String name, String description, double price, int quantity) {
        Product product = getProductById(productId);
        if (product != null) {
            product.updateProductDetails(name, description, price, quantity);
            System.out.println("Product updated successfully!");
        }
    }

    public void deleteProduct(String productId) {
        Product product = getProductById(productId);
        if (product != null) {
            getProducts().remove(product);
            System.out.println("Product deleted successfully!");
        }
    }
}
