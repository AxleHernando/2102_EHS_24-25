package com.ehub_sales.e.hub_sales.Users;

import com.ehub_sales.e.hub_sales.Sales.Inventory;
import com.ehub_sales.e.hub_sales.Sales.Product;
import com.ehub_sales.e.hub_sales.Sales.SalesReport;

public class Admin extends User {
    public Admin(String userId, String username, String password, SalesReport salesReports) {
        super(userId, username, password);
        this.generateSalesReport(salesReports);
    }

    // Admin-specific methods for product management
    public void addProduct(Inventory inventory, Product product) {
        inventory.addProduct(product);
    }

    public void viewProducts(Inventory inventory) {
        inventory.viewProducts();
    }

    public void updateProduct(Inventory inventory, String productId, String name, String description, double price,
            int quantity) {
        inventory.updateProduct(productId, name, description, price, quantity);
    }

    public void deleteProduct(Inventory inventory, String productId) {
        inventory.deleteProduct(productId);
    }

    public void generateSalesReport(SalesReport salesReport) {
        salesReport.generateReport();
    }
    
    public void manageProduct(Inventory inventory, Product product, String action) {
        switch (action) {
            case "add":
                inventory.addProduct(product);
                break;
            case "update":
                inventory.updateProduct(product.getProductId(), product.getProductName(), product.getDescription(),
                        product.getPrice(), product.getQuantity());
                break;
            case "delete":
                inventory.deleteProduct(product.getProductId());
                break;
            default:
                System.out.println("Invalid action specified.");
        }
    }
}
