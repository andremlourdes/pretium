package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações com Cupons e Itens de Compra.
 */
@Service
@Transactional
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    /**
     * Cria um novo cupom de compra e registra no histórico de preços.
     */
    public Receipt createReceipt(Long storeId, String receiptNumber, LocalDateTime purchaseDate, BigDecimal totalAmount) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        Receipt receipt = new Receipt();
        receipt.setStore(store);
        receipt.setReceiptNumber(receiptNumber);
        receipt.setPurchaseDate(purchaseDate);
        receipt.setTotalAmount(totalAmount);
        receipt.setStatus("ACTIVE");
        receipt.setCreatedAt(LocalDateTime.now());
        receipt.setUpdatedAt(LocalDateTime.now());

        return receiptRepository.save(receipt);
    }

    /**
     * Adiciona um item de compra ao cupom e registra no histórico de preços.
     */
    public PurchaseItem addItemToReceipt(Long receiptId, Long productId, Integer quantity, BigDecimal unitPrice) {
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

        PurchaseItem item = new PurchaseItem();
        item.setReceipt(receipt);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setTotalPrice(totalPrice);

        PurchaseItem saved = purchaseItemRepository.save(item);

        // Registra no histórico de preços
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setStore(receipt.getStore());
        priceHistory.setPrice(unitPrice);
        priceHistory.setRecordedDate(LocalDateTime.now());

        priceHistoryRepository.save(priceHistory);

        return saved;
    }

    /**
     * Obtém um cupom por ID.
     */
    public Optional<Receipt> getReceiptById(Long id) {
        return receiptRepository.findById(id);
    }

    /**
     * Obtém todos os cupons.
     */
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    /**
     * Obtém cupons de uma loja específica.
     */
    public List<Receipt> getReceiptsByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        return receiptRepository.findByStore(store);
    }

    /**
     * Obtém cupons em um período específico.
     */
    public List<Receipt> getReceiptsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return receiptRepository.findReceiptsByDateRange(startDate, endDate);
    }

    /**
     * Obtém itens de um cupom.
     */
    public List<PurchaseItem> getReceiptItems(Long receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));
        return purchaseItemRepository.findByReceipt(receipt);
    }

    /**
     * Remove um item de compra.
     */
    public void removeItem(Long itemId) {
        purchaseItemRepository.deleteById(itemId);
    }

    /**
     * Calcula o total de um cupom baseado em seus itens.
     */
    public BigDecimal calculateReceiptTotal(Long receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        return receipt.getItems().stream()
            .map(PurchaseItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Marca um cupom como cancelado.
     */
    public void cancelReceipt(Long receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        receipt.setStatus("CANCELLED");
        receipt.setUpdatedAt(LocalDateTime.now());
        receiptRepository.save(receipt);
    }

    /**
     * Atualiza informações do cupom.
     */
    public Receipt updateReceipt(Long receiptId, BigDecimal totalAmount, String notes) {
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

        receipt.setTotalAmount(totalAmount);
        receipt.setNotes(notes);
        receipt.setUpdatedAt(LocalDateTime.now());

        return receiptRepository.save(receipt);
    }
}




