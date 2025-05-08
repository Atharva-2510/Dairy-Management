package com.DM.dairyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DM.dairyManagement.model.OtherExpense;

@Repository
public interface OtherExpenseRepository extends JpaRepository<OtherExpense, Long> {
}