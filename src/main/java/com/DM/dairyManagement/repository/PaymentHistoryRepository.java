package com.DM.dairyManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.DM.dairyManagement.model.PaymentHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByPaymentId(Long paymentId);
    List<PaymentHistory> findTop10ByOrderByDateDesc();
    List<PaymentHistory> findByNameContainingIgnoreCase(String name);
    List<PaymentHistory> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PaymentHistory> findByNameContainingIgnoreCaseAndDateBetween(String name, LocalDateTime startDate, LocalDateTime endDate);
    Page<PaymentHistory> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}