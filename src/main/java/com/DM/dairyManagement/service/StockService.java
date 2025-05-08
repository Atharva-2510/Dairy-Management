package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Product;
import com.DM.dairyManagement.model.Stock;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private ProductRepository productRepository;

    // Fetch all stock records
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    
    // Save a new or updated stock
    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    // Get a stock by ID (for editing/updating)
    public Optional<Stock> getStockById(Long stockId) {
        return stockRepository.findById(stockId);
    }

    // Delete a stock record by stock ID
    public void deleteStockById(Long stockId) {
        stockRepository.deleteById(stockId);
    }

    // Delete stock by product ID
    public void deleteStockByProductId(Long productId) {
        stockRepository.deleteByProduct_Id(productId);
    }
}
