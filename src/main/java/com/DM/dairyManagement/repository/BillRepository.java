package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;	
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findTop5ByOrderByIdDesc();

    List<Bill> findByFullName(String fullName);

    @Query("SELECT COALESCE(MAX(b.billNo), 0) + 1 FROM Bill b")
    Long findNextBillNo();

    List<Bill> findByCustomerType(String customerType); // Filter by Customer Type
}
