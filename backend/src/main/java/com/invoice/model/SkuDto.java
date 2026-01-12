package com.invoice.model;

public class SkuDto {
    public String sku;
    public String name;
    public double price;

    public SkuDto(String sku, String name, double price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }
}
