package org.example.controller;

import org.example.dto.ReceiptDTO;
import org.example.dto.PurchaseItemDTO;
import org.example.service.ReceiptService;
import org.example.entity.Receipt;
import org.example.entity.PurchaseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciar Cupons e Itens de Compra.
 *
 * Endpoints:
 * - POST /api/receipts - Criar novo cupom
 * - GET /api/receipts/{id} - Obter cupom
 * - GET /api/receipts - Listar cupons
 * - GET /api/receipts/store/{storeId} - Cupons de uma loja
 * - POST /api/receipts/{receiptId}/items - Adicionar item
 * - DELETE /api/receipts/{receiptId} - Cancelar cupom
 */
@RestController
@RequestMapping("/api/receipts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    /**
     * Cria um novo cupom de compra.
     */
    @PostMapping
    public ResponseEntity<ReceiptDTO> createReceipt(
            @RequestParam Long storeId,
            @RequestParam String receiptNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime purchaseDate,
            @RequestParam BigDecimal totalAmount) {
        try {
            Receipt receipt = receiptService.createReceipt(storeId, receiptNumber, purchaseDate, totalAmount);
            ReceiptDTO dto = convertToDTO(receipt);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtém um cupom por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDTO> getReceiptById(@PathVariable Long id) {
        try {
            Receipt receipt = receiptService.getReceiptById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
            return ResponseEntity.ok(convertToDTO(receipt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Lista todos os cupons.
     */
    @GetMapping
    public ResponseEntity<List<ReceiptDTO>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();
        List<ReceiptDTO> dtos = receipts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtém cupons de uma loja específica.
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReceiptDTO>> getReceiptsByStore(@PathVariable Long storeId) {
        try {
            List<Receipt> receipts = receiptService.getReceiptsByStore(storeId);
            List<ReceiptDTO> dtos = receipts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtém cupons em um período específico.
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<ReceiptDTO>> getReceiptsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<Receipt> receipts = receiptService.getReceiptsByDateRange(startDate, endDate);
            List<ReceiptDTO> dtos = receipts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Adiciona um item a um cupom.
     */
    @PostMapping("/{receiptId}/items")
    public ResponseEntity<PurchaseItemDTO> addItemToReceipt(
            @PathVariable Long receiptId,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam BigDecimal unitPrice) {
        try {
            PurchaseItem item = receiptService.addItemToReceipt(receiptId, productId, quantity, unitPrice);
            PurchaseItemDTO dto = convertItemToDTO(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtém itens de um cupom.
     */
    @GetMapping("/{receiptId}/items")
    public ResponseEntity<List<PurchaseItemDTO>> getReceiptItems(@PathVariable Long receiptId) {
        try {
            List<PurchaseItem> items = receiptService.getReceiptItems(receiptId);
            List<PurchaseItemDTO> dtos = items.stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Remove um item de um cupom.
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        try {
            receiptService.removeItem(itemId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Cancela um cupom.
     */
    @PutMapping("/{receiptId}/cancel")
    public ResponseEntity<ReceiptDTO> cancelReceipt(@PathVariable Long receiptId) {
        try {
            receiptService.cancelReceipt(receiptId);
            Receipt receipt = receiptService.getReceiptById(receiptId)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
            return ResponseEntity.ok(convertToDTO(receipt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Atualiza informações do cupom.
     */
    @PutMapping("/{receiptId}")
    public ResponseEntity<ReceiptDTO> updateReceipt(
            @PathVariable Long receiptId,
            @RequestParam BigDecimal totalAmount,
            @RequestParam(required = false) String notes) {
        try {
            Receipt receipt = receiptService.updateReceipt(receiptId, totalAmount, notes);
            return ResponseEntity.ok(convertToDTO(receipt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Converte Receipt para ReceiptDTO.
     */
    private ReceiptDTO convertToDTO(Receipt receipt) {
        ReceiptDTO dto = new ReceiptDTO();
        dto.setId(receipt.getId());
        dto.setStoreId(receipt.getStore().getId());
        dto.setStoreName(receipt.getStore().getName());
        dto.setReceiptNumber(receipt.getReceiptNumber());
        dto.setPurchaseDate(receipt.getPurchaseDate());
        dto.setTotalAmount(receipt.getTotalAmount());
        dto.setDiscountAmount(receipt.getDiscountAmount());
        dto.setTaxAmount(receipt.getTaxAmount());
        dto.setPaymentMethod(receipt.getPaymentMethod());
        dto.setNotes(receipt.getNotes());
        dto.setStatus(receipt.getStatus());
        dto.setCreatedAt(receipt.getCreatedAt());
        dto.setUpdatedAt(receipt.getUpdatedAt());
        return dto;
    }

    /**
     * Converte PurchaseItem para PurchaseItemDTO.
     */
    private PurchaseItemDTO convertItemToDTO(PurchaseItem item) {
        PurchaseItemDTO dto = new PurchaseItemDTO();
        dto.setId(item.getId());
        dto.setReceiptId(item.getReceipt().getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setDiscountAmount(item.getDiscountAmount());
        dto.setTaxAmount(item.getTaxAmount());
        dto.setItemNumber(item.getItemNumber());
        return dto;
    }
}



