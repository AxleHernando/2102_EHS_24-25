package com.ehub_sales.e.hub_sales.Sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesReport {
    private List<SalesTransaction> transactions;

    public SalesReport() {
        setTransactions(transactions = new ArrayList<>());
    }

    public void setTransactions(List<SalesTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<SalesTransaction> getTransactions() {
        return transactions;
    }

    public void recordTransaction(String productId, String customerId, int quantitySold) {
        SalesTransaction transaction = new SalesTransaction(productId, customerId, quantitySold, new Date());
        getTransactions().add(transaction);
    }

    public void generateReport() {
        System.out.println("Sales Report:");
        for (SalesTransaction transaction : getTransactions()) {
            System.out.println(transaction);
        }
    }
}

class SalesTransaction {
    private String productId;
    private String customerId;
    private int quantitySold;
    private Date date;

    public SalesTransaction(String productId, String customerId, int quantitySold, Date date) {
        setProductId(productId);
        setCustomerId(customerId);
        setQuantitySold(quantitySold);
        setDate(date);
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Product ID: " + getProductId() + ", Customer ID: " + getCustomerId() + ", Quantity: "
                + getQuantitySold() + ", Date: " + getDate();
    }
}
