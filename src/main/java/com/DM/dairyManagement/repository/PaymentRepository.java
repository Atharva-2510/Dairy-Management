package com.DM.dairyManagement.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.DM.dairyManagement.model.Payment;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBillId(Long billId);
    List<Payment> findTop3ByOrderByPaymentDateDesc();
    long countByDueAmount(double amount);
    long countByDueAmountGreaterThan(double amount);
	Page<Payment> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}