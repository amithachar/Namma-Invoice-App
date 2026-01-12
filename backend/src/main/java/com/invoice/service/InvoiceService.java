package com.invoice.service;

import com.invoice.model.Invoice;
import com.invoice.model.InvoiceItem;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    public Invoice calculate(Invoice invoice) {

        double sub = 0;
        for (InvoiceItem item : invoice.items) {
            sub += item.amount();
        }

        invoice.subTotal = sub;
        invoice.tax = sub * (invoice.taxRate / 100);
        invoice.total = invoice.subTotal + invoice.tax;

        invoice.invoiceNo = "INV-" + System.currentTimeMillis();
        return invoice;
    }
}
