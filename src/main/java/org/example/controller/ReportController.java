package org.example.controller;

import org.example.service.ReceiptReportPdfService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReceiptReportPdfService receiptReportPdfService;

    public ReportController(ReceiptReportPdfService receiptReportPdfService) {
        this.receiptReportPdfService = receiptReportPdfService;
    }

    @GetMapping(value = "/receipt/{receiptId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> receiptPdf(@PathVariable Long receiptId) {
        byte[] report = receiptReportPdfService.generateReceipt(receiptId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
            .filename("pretium-cupom-" + receiptId + ".pdf")
            .build());
        return ResponseEntity.ok().headers(headers).body(report);
    }
}

