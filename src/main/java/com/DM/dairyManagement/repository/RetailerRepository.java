package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    List<Retailer> findTop5ByOrderByIdDesc();
}
