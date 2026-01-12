package com.invoice.model;

public class InvoiceItem {
    public String sku;
    public String name;
    public int qty;
    public double price;

    public double amount() {
        return qty * price;
    }
}
