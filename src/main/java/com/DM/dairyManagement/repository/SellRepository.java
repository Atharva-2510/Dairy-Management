package com.DM.dairyManagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DM.dairyManagement.model.Sell;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
    List<Sell> findByDateBetween(LocalDate start, LocalDate end);
}

