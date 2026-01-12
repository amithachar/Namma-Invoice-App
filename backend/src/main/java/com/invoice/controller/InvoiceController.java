package com.invoice.controller;

import com.invoice.model.Invoice;
import com.invoice.model.SkuDto;
import com.invoice.service.InvoiceService;
import com.invoice.service.PdfService;
import com.invoice.service.SkuService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private final SkuService skuService;
    private final InvoiceService invoiceService;
    private final PdfService pdfService;

    public InvoiceController(
            SkuService skuService,
            InvoiceService invoiceService,
            PdfService pdfService
    ) {
        this.skuService = skuService;
        this.invoiceService = invoiceService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public String invoicePage() {
        return "invoice";
    }

    @GetMapping("/skus")
    @ResponseBody
    public List<SkuDto> getSkus() throws Exception {
        return skuService.loadSkus();
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Invoice invoice) {
        try {
            Invoice calculated = invoiceService.calculate(invoice);
            byte[] pdf = pdfService.generate(calculated);

            System.out.println("PDF generated, size = " + pdf.length);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + calculated.invoiceNo + ".pdf"
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
