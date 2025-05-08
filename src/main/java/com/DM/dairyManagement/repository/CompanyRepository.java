package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findTop5ByOrderByIdDesc();
}
