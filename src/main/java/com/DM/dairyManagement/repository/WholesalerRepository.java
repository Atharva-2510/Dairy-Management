package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Wholesaler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WholesalerRepository extends JpaRepository<Wholesaler, Long> {
    List<Wholesaler> findTop5ByOrderByIdDesc();
}