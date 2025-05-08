package com.DM.dairyManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Payment;
import com.DM.dairyManagement.model.PaymentHistory;
import com.DM.dairyManagement.repository.BillRepository;
import com.DM.dairyManagement.repository.PaymentHistoryRepository;
import com.DM.dairyManagement.repository.PaymentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    
    @Autowired
    private BillRepository billRepository;
    
    public Payment savePayment(Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);
        // Create a history record
        PaymentHistory history = new PaymentHistory("Payment Added", savedPayment);
        paymentHistoryRepository.save(history);
        return savedPayment;
    }
    
    public List<Payment> getPaymentsByBillId(Long billId) {
        return paymentRepository.findByBillId(billId);
    }
    
    public List<Bill> findBillByCustomerName(String customerName) {
        return billRepository.findByFullName(customerName);
    }
    
    public List<Payment> getLatestPayments() {
        return paymentRepository.findTop3ByOrderByPaymentDateDesc();
    }
    
    public long getTotalPayments() {
        return paymentRepository.count();
    }
    
    public long getCompletedTransactionCount() {
        return paymentRepository.countByDueAmount(0);
    }
    
    public long getIncompleteTransactionCount() {
        return paymentRepository.countByDueAmountGreaterThan(0);
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public List<Payment> getLatestTransactions() {
        return paymentRepository.findTop3ByOrderByPaymentDateDesc();
    }
    
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
    public void updatePayment(Payment payment) {
        Payment existingPayment = paymentRepository.findById(payment.getId()).orElse(null);
        
        if (existingPayment != null) {
            existingPayment.setPaidAmount(payment.getPaidAmount());
            existingPayment.setDueAmount(payment.getAmount().subtract(payment.getPaidAmount()));
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            paymentRepository.save(existingPayment);
            
            // Create a history record for the update
            PaymentHistory history = new PaymentHistory("Payment Updated", existingPayment);
            paymentHistoryRepository.save(history);
        }
    }
    
    public void save(Payment payment) {
        // This is probably a redundant method - using savePayment instead
        Payment savedPayment = paymentRepository.save(payment);
        PaymentHistory history = new PaymentHistory("Payment Added", savedPayment);
        paymentHistoryRepository.save(history);
    }
    
    // PAYMENT HISTORY METHODS
    
    public List<PaymentHistory> getAllPaymentHistory() {
        return paymentHistoryRepository.findAll();
    }
    
    public List<PaymentHistory> getLatestPaymentHistory() {
        return paymentHistoryRepository.findTop10ByOrderByDateDesc();
    }
    
    public List<PaymentHistory> getPaymentHistoryByPaymentId(Long paymentId) {
        return paymentHistoryRepository.findByPaymentId(paymentId);
    }
    
    public List<PaymentHistory> searchPaymentHistory(String name, LocalDate fromDate, LocalDate toDate) {
        if (name == null) name = "";
        
        if (fromDate != null && toDate != null) {
            LocalDateTime startDateTime = fromDate.atStartOfDay();
            LocalDateTime endDateTime = toDate.atTime(LocalTime.MAX);
            return paymentHistoryRepository.findByNameContainingIgnoreCaseAndDateBetween(name, startDateTime, endDateTime);
        } else if (fromDate != null) {
            LocalDateTime startDateTime = fromDate.atStartOfDay();
            LocalDateTime endDateTime = LocalDate.now().atTime(LocalTime.MAX);
            return paymentHistoryRepository.findByNameContainingIgnoreCaseAndDateBetween(name, startDateTime, endDateTime);
        } else if (toDate != null) {
            LocalDateTime startDateTime = LocalDate.of(2000, 1, 1).atStartOfDay();
            LocalDateTime endDateTime = toDate.atTime(LocalTime.MAX);
            return paymentHistoryRepository.findByNameContainingIgnoreCaseAndDateBetween(name, startDateTime, endDateTime);
        } else {
            return paymentHistoryRepository.findByNameContainingIgnoreCase(name);
        }
    }
}