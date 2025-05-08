package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "returned_quantity")
    private String returnedQuantity = "0";

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    // Constructors
    public Stock() {}

    public Stock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.returnedQuantity = "0";
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters
    public Long getStockId() {
        return stockId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReturnedQuantity() {
        return (returnedQuantity == null || returnedQuantity.trim().isEmpty()) ? "0" : returnedQuantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getProductName() {
        return product != null ? product.getName() : "N/A";
    }

    // Setters
    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReturnedQuantity(String returnedQuantity) {
        this.returnedQuantity = (returnedQuantity == null || returnedQuantity.trim().isEmpty()) ? "0" : returnedQuantity;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Auto-update lastUpdated
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    // ✅ Remaining Quantity (calculated)
    public int getRemainingQuantity() {
        try {
            int totalQty =getQuantity();
            int returnedQty = Integer.parseInt(getReturnedQuantity());
            return totalQty - returnedQty;
        } catch (NumberFormatException e) {
            return 0; // fallback
        }
    }
}
