package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {

    void deleteByProduct_Id(Long productId);

    // ✅ Recommended: clean and reliable version using JPQL
    @Query("SELECT s FROM Stock s WHERE LOWER(TRIM(s.product.name)) = LOWER(TRIM(:name))")
    Optional<Stock> findStockByProductNameIgnoreCase(@Param("name") String name);
}
