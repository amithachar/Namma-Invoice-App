package com.invoice.model;

import java.util.List;

public class Invoice {
    public String invoiceNo;
    public String billTo;
    public String soldTo;
    public String gstNumber;
    public String poNumber;
    public String salesPerson;
    public String date;

    public double taxRate;
    public double subTotal;
    public double tax;
    public double total;

    public List<InvoiceItem> items;
}
