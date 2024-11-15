package com.ehub_sales.e.hub_sales.Sales;

public class Product {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String supplierId;

    public Product() {

    }

    public Product(String productId, String productName, String description, double price, int quantity,
            String supplierId) {
        setProductId(productId);
        setProductName(productName);
        setDescription(description);
        setPrice(price);
        setQuantity(quantity);
        setSupplierId(supplierId);
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateProductDetails(String name, String description, double price, int quantity) {
        setProductName(name);
        setDescription(description);
        setPrice(price);
        setQuantity(quantity);
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplierId() {
        return supplierId;
    }
}
