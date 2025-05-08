package com.DM.dairyManagement.model;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long billId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal paidAmount = BigDecimal.ZERO;

    private String name;  // Or the appropriate type for name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(nullable = false)
    private BigDecimal dueAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = true)
    private String accountNumber;  // For Bank Transfer

    @Column(nullable = true)
    private String creditCardNumber;  // For Credit Card

    @Column(nullable = true)
    private String upiId;  // For UPI

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    // Default Constructor
    public Payment() {}

    // Constructor matching the error message
    public Payment(Long billId, BigDecimal amount, BigDecimal paidAmount, BigDecimal dueAmount, 
                   String paymentMethod, PaymentStatus paymentStatus) {
        this.billId = billId;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueAmount = dueAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Full Constructor
    public Payment(Long billId, BigDecimal amount, BigDecimal paidAmount, BigDecimal dueAmount, 
                   String paymentMethod, String accountNumber, String creditCardNumber, 
                   String upiId, PaymentStatus paymentStatus) {
        this.billId = billId;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueAmount = dueAmount;
        this.paymentMethod = paymentMethod;
        this.accountNumber = accountNumber;
        this.creditCardNumber = creditCardNumber;
        this.upiId = upiId;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getPaidAmount() { return paidAmount; }
    public void setPaidAmount(BigDecimal paidAmount) { this.paidAmount = paidAmount; }

    public BigDecimal getDueAmount() { return dueAmount; }
    public void setDueAmount(BigDecimal dueAmount) { this.dueAmount = dueAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getCreditCardNumber() { return creditCardNumber; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }

    public String getUpiId() { return upiId; }
    public void setUpiId(String upiId) { this.upiId = upiId; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED
    }
}
