package org.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.entity.PurchaseItem;
import org.example.entity.Receipt;
import org.example.repository.ReceiptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReceiptReportPdfService {

    private final ReceiptRepository receiptRepository;

    public ReceiptReportPdfService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public byte[] generateReceipt(Long receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        try (PDDocument document = new PDDocument(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 16);
                content.newLineAtOffset(50, 750);
                content.showText("Pretium - Relatório de Cupom");
                content.setFont(PDType1Font.HELVETICA, 11);
                content.newLineAtOffset(0, -30);
                content.showText("Cupom: " + safe(receipt.getReceiptNumber()));
                content.newLineAtOffset(0, -18);
                content.showText("Loja: " + safe(receipt.getStore().getName()));
                content.newLineAtOffset(0, -18);
                content.showText("Data: " + safe(receipt.getPurchaseDate()));
                content.newLineAtOffset(0, -30);
                content.showText("Itens:");
                if (receipt.getItems() != null) {
                    for (PurchaseItem item : receipt.getItems()) {
                        content.newLineAtOffset(0, -18);
                        content.showText(safe(item.getProduct().getName()) + " - " + safe(item.getTotalPrice()));
                    }
                }
                content.newLineAtOffset(0, -30);
                content.showText("Total: " + safe(receipt.getTotalAmount()));
                content.endText();
            }
            document.save(output);
            return output.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível gerar o relatório PDF", exception);
        }
    }

    private String safe(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).replaceAll("[^\\x20-\\x7E]", "?");
    }
}


