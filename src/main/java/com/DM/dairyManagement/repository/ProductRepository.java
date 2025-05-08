package com.DM.dairyManagement.repository;
import com.DM.dairyManagement.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Corrected method for fetching the latest 5 products
    default List<Product> findTop5ByOrderByIdDesc() {
        return findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }
}
