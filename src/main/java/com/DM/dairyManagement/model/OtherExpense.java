package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class OtherExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lightBill;
    private Double dieselExpense;
    private Double shopRent;
    private Double otherExpense;
    private Double totalAmount;

    private LocalDate date;

    public OtherExpense() {}

    public OtherExpense(Double lightBill, Double dieselExpense, Double shopRent, Double otherExpense, LocalDate date) {
        this.lightBill = lightBill;
        this.dieselExpense = dieselExpense;
        this.shopRent = shopRent;
        this.otherExpense = otherExpense;
        this.date = date;
        calculateTotal();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getLightBill() { return lightBill; }
    public void setLightBill(Double lightBill) { this.lightBill = lightBill; calculateTotal(); }

    public Double getDieselExpense() { return dieselExpense; }
    public void setDieselExpense(Double dieselExpense) { this.dieselExpense = dieselExpense; calculateTotal(); }

    public Double getShopRent() { return shopRent; }
    public void setShopRent(Double shopRent) { this.shopRent = shopRent; calculateTotal(); }

    public Double getOtherExpense() { return otherExpense; }
    public void setOtherExpense(Double otherExpense) { this.otherExpense = otherExpense; calculateTotal(); }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    private void calculateTotal() {
        this.totalAmount = (this.lightBill != null ? this.lightBill : 0) +
                           (this.dieselExpense != null ? this.dieselExpense : 0) +
                           (this.shopRent != null ? this.shopRent : 0) +
                           (this.otherExpense != null ? this.otherExpense : 0);
    }
}
