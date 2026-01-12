package com.invoice.service;

import com.invoice.model.Invoice;
import com.invoice.model.InvoiceItem;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generate(Invoice inv) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        // SAFE LOGO LOAD
        try {
            ClassPathResource logoRes = new ClassPathResource("static/logo.png");
            if (logoRes.exists()) {
                Image logo = new Image(
                    ImageDataFactory.create(logoRes.getInputStream().readAllBytes())
                );
                logo.setWidth(120);
                doc.add(logo);
            }
        } catch (Exception e) {
            System.out.println("Logo skipped: " + e.getMessage());
        }

        doc.add(new Paragraph("Namma Invoice").setBold().setFontSize(18));
        doc.add(new Paragraph("Invoice No: " + inv.invoiceNo));
        doc.add(new Paragraph("Bill To: " + inv.billTo));
        doc.add(new Paragraph("Sold To: " + inv.soldTo));
        doc.add(new Paragraph("GST: " + inv.gstNumber));
        doc.add(new Paragraph("PO: " + inv.poNumber));
        doc.add(new Paragraph("Sales Person: " + inv.salesPerson));
        doc.add(new Paragraph("Date: " + inv.date));
        doc.add(new Paragraph(" "));

        Table table = new Table(5);
        table.addCell("SKU");
        table.addCell("Product");
        table.addCell("Qty");
        table.addCell("Price");
        table.addCell("Amount");

        for (InvoiceItem it : inv.items) {
            table.addCell(it.sku);
            table.addCell(it.name);
            table.addCell(String.valueOf(it.qty));
            table.addCell(String.valueOf(it.price));
            table.addCell(String.valueOf(it.amount()));
        }

        doc.add(table);
        doc.add(new Paragraph("Subtotal: " + inv.subTotal));
        doc.add(new Paragraph("Tax (" + inv.taxRate + "%): " + inv.tax));
        doc.add(new Paragraph("Total: " + inv.total).setBold());

        doc.close();
        return out.toByteArray();
    }
}
