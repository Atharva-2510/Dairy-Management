package com.DM.dairyManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DM.dairyManagement.model.Payment;
import com.DM.dairyManagement.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService1 {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Page<Payment> getAllPaymenPage(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Page<Payment> searchPayments(String keyword, Pageable pageable) {
        return paymentRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

	@Override
	public Page<Payment> getAllPayments(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}