package com.DM.dairyManagement.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.DM.dairyManagement.model.Payment;

public interface PaymentService1 {
    Page<Payment> getAllPayments(Pageable pageable);
    Page<Payment> searchPayments(String keyword, Pageable pageable);
	Page<Payment> getAllPaymenPage(Pageable pageable);
}