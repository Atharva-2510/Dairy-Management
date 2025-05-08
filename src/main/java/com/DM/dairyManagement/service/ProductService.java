package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Product;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public ProductService(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional(readOnly = true)
    public long getTotalProducts() {
        return productRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Product> getLatestProducts() {
        return productRepository.findTop5ByOrderByIdDesc();
    }
    
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    // Corrected method to delete a product along with its stock
    @Transactional
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Delete associated stock records
        stockRepository.deleteByProduct_Id(productId);

        // Now delete the product
        productRepository.delete(product);
    }
}
